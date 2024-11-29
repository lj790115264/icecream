package com.yl.config.db;

import com.yl.DevUrlFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.session.ResultHandler;

import java.sql.Statement;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Intercepts({
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = {Statement.class})
})
@Slf4j
public class DevUrlInterceptor implements Interceptor {

    //定义正则表达式，提取出sql语句中的关键字
    private static final Pattern TABLE_NAME_PATTERN = Pattern.compile("(FROM|UPDATE)\\s+([\\w\\._]+)");

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        BoundSql boundSql = statementHandler.getBoundSql();
        String sql = boundSql.getSql();

        // 解析SQL语句，提取表名称
        Matcher matcher = TABLE_NAME_PATTERN.matcher(sql);
        while (matcher.find()) {
            String tableName = matcher.group(2);
            Set<String> tables = DevUrlFilter.tableThreadLocal.get();
            if (null != tables && null != tableName) {
                tables.add(tableName.toLowerCase());
                log.info("操作了数据库表，名称为: {}", tableName);
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        // 这里可以用来配置拦截器的属性
    }
}


