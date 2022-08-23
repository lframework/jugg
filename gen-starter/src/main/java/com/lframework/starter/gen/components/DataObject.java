package com.lframework.starter.gen.components;

import com.lframework.starter.gen.dto.dataobj.GenGenerateInfoDto;
import com.lframework.starter.gen.enums.DataObjectType;
import java.util.List;
import lombok.Data;

/**
 * 数据对象
 */
@Data
public class DataObject {

  /**
   * ID
   */
  private String id;

  /**
   * 编号
   */
  private String code;

  /**
   * 名称
   */
  private String name;

  /**
   * 类型
   */
  private DataObjectType type;

  /**
   * 备注
   */
  private String description;

  /**
   * 数据表信息
   */
  private Table table;

  /**
   * 字段信息
   */
  private List<DataObjectColumn> columns;

  /**
   * 配置信息
   */
  private GenGenerateInfoDto generateInfo;
}
