package com.lframework.starter.mybatis.dto;

import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 操作日志Dto
 *
 * @author zmj
 */
@Data
public class DefaultOpLogsDto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 日志名称
     */
    private String name;

    /**
     * 类别
     */
    private OpLogType logType;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 补充信息
     */
    private String extra;

    /**
     * 创建人ID
     */
    private String createBy;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
