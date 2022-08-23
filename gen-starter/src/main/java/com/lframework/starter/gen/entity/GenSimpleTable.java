package com.lframework.starter.gen.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.gen.enums.GenConvertType;
import com.lframework.starter.mybatis.entity.BaseEntity;
import com.lframework.starter.web.dto.BaseDto;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-05-28
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gen_simple_table")
public class GenSimpleTable extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  private String id;

  /**
   * 数据表所属的数据库名
   */
  private String tableSchema;

  /**
   * 数据库表名
   */
  private String tableName;

  /**
   * 数据库引擎
   */
  private String engine;

  /**
   * 字符校验编码集
   */
  private String tableCollation;

  /**
   * 备注
   */
  private String tableComment;

  /**
   * 转换方式
   */
  private GenConvertType convertType;

  /**
   * 创建时间 新增时赋值
   */
  @TableField(fill = FieldFill.INSERT)
  private LocalDateTime createTime;
}
