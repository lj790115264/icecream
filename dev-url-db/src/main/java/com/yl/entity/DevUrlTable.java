package com.yl.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author andre.lan
 */
@Data
public class DevUrlTable {

    private Long id;

    private String uri;
    private String tables;
    private String crossPath;
    private LocalDateTime createTime;
}
