package com.lframework.gen.generate.templates;

import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * 修改功能Template
 */
@Data
public class UpdateTemplate {

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

    /**
     * 主键
     */
    private List<Key> keys;

    @Data
    public static class Key {

        /**
         * 字段类型
         */
        private String type;

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
         * 字段备注
         */
        private String description;
    }

    @Data
    public static class Column {

        /**
         * 是否主键
         */
        private Boolean isKey;

        /**
         * 是否必填
         */
        private Boolean required;

        /**
         * 验证注解
         */
        private String validateAnno;

        /**
         * 验证信息
         * 当required == true时生效
         */
        private String validateMsg;

        /**
         * 是否内置枚举
         */
        private Boolean fixEnum;

        /**
         * 枚举的Code的类型
         * 当fixEnum == true时生效
         */
        private String enumCodeType;

        /**
         * 字段类型
         */
        private String type;

        /**
         * 前端字段类型
         * 只有字段是枚举时生效，此值为前端枚举类型
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
         * 正则表达式
         */
        private String regularExpression;

        /**
         * 字段备注
         */
        private String description;
    }
}
