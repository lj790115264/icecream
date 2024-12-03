package com.yl.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.yl.DevUrlFilter;
import com.yl.JsonUtil;
import com.yl.id.IdGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import com.yl.entity.DevUrlTable;
import com.yl.mapper.DevUrlTableMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
public class DevUrlTableDbImpl implements DevUrlTableService {

    static ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 10, 3, TimeUnit.MINUTES, new LinkedBlockingQueue<>(),
            r -> new Thread(r, "dev-url_" + r.hashCode()),
            new ThreadPoolExecutor.CallerRunsPolicy());

    @Autowired
    @Lazy
    private DevUrlTableDbImpl self;

    @Autowired
    private DevUrlTableMapper devUrlTableMapper;

    @Autowired
    private IdGenerator<Long> idGenerator;

    @Override
    public void doing() {

        String uri = DevUrlFilter.urlThreadLocal.get();
        List<String> tables = new ArrayList<>(DevUrlFilter.tableThreadLocal.get());
        DevUrlFilter.tableThreadLocal.remove();
        List<String> urlCrosses = DevUrlFilter.urlCrossThreadLocal.get();
        String crossPath = JsonUtil.toJson(urlCrosses);
        if (crossPath.length() >= 2000) {
            crossPath = crossPath.substring(0, 2000);
        }
        DevUrlFilter.urlCrossThreadLocal.remove();

        log.info("header {} tables {}", uri, tables);
        for (String table : tables) {
            String finalUri = uri;
            String finalCrossPath = crossPath;
            executor.submit(() -> self.save(finalUri, table, finalCrossPath));
        }
    }

    public void save(String uri, String table, String crossPath) {

        DevUrlTable devUrlTable = new DevUrlTable();
        devUrlTable.setUri(uri);
        devUrlTable.setTables(table);
        devUrlTable.setCrossPath(crossPath);
        devUrlTable.setCreateTime(LocalDateTime.now());
        Integer count = devUrlTableMapper
                .selectCount(new LambdaQueryWrapper<DevUrlTable>().eq(DevUrlTable::getUri, uri)
                        .eq(DevUrlTable::getTables, table));
        if (count == 0) {
            devUrlTableMapper.insert(devUrlTable);
        }
    }
}
