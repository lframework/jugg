package com.lframework.starter.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.enums.OpLogType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * <p>
 * 操作日志实体类
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("op_logs")
public class DefaultOpLogs extends BaseEntity {

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
     * 新增时赋值
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     * 新增时赋值
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
