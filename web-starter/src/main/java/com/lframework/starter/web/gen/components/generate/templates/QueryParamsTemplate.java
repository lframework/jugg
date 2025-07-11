package com.lframework.starter.web.gen.components.generate.templates;

import java.util.List;
import java.util.Set;
import lombok.Data;

/**
 * 查询参数Template
 */
@Data
public class QueryParamsTemplate {

  /**
   * 包名
   */
  private String packageName;

  /**
   * 类名
   */
  private String className;

  /**
   * 模块名称
   */
  private String moduleName;

  /**
   * 业务名称
   */
  private String bizName;

  /**
   * 类描述
   */
  private String classDescription;

  /**
   * 作者
   */
  private String author;

  /**
   * 字段
   */
  private List<Column> columns;

  /**
   * 需要import的包
   */
  private Set<String> importPackages;

  @Data
  public static class Column {

    /**
     * 是否内置枚举
     */
    private Boolean fixEnum;

    /**
     * 正则表达式
     */
    private String regularExpression;

    /**
     * 枚举的Code的类型 当fixEnum == true时生效
     */
    private String enumCodeType;

    /**
     * 字段类型
     */
    private String dataType;

    /**
     * 前端字段类型
     */
    private String frontDataType;

    /**
     * 前端字段类型 只有字段是枚举时生效，此值为前端枚举类型
     */
    private String frontType;

    /**
     * 显示类型
     */
    private Integer viewType;

    /**
     * 字段名称
     */
    private String name;

    /**
     * 字段名称（首字母大写）
     */
    private String nameProperty;

    /**
     * 字段名
     */
    private String columnName;

    /**
     * 查询类型
     */
    private Integer queryType;

    /**
     * 字段备注
     */
    private String description;

    /**
     * 是否包含状态Tag
     */
    private Boolean hasAvailableTag = Boolean.FALSE;

    /**
     * 数据字典Code
     */
    private String dataDicCode;

    /**
     * 自定义选择器ID
     */
    private String customSelectorId;
  }
}
