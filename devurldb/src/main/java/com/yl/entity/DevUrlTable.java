package com.yl.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author andre.lan
 */
@Data
@TableName("DEV_URL_TABLE")
public class DevUrlTable {

    @TableId(value = "id", type = IdType.ID_WORKER)
    private Long id;

    private String uri;
    private String tables;
    private LocalDateTime createTime;
}
