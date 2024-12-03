package com.yl.mapper;

import com.yl.entity.DevUrlTable;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * @author andre.lan
 */
@Mapper
public interface DevUrlTableMapper {


    @Select("select count(1) as c from DEV_URL_TABLE where uri = #{uri} and tables = #{tables}")
    int count(@Param("uri") String uri, @Param("tables") String tables);

    @Insert("insert into DEV_URL_TABLE (uri, tables, create_time, cross_path) values (#{uri}, #{createTime}, #{crossPath} )")
    void insert(DevUrlTable devUrlTable);
}
