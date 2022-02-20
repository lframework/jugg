package com.lframework.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.gen.enums.GenKeyType;
import com.lframework.gen.enums.GenTemplateType;
import com.lframework.starter.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-12-08
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("gen_generate_info")
public class GenGenerateInfo extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 生成模板类型
     */
    private GenTemplateType templateType;

    /**
     * 包名
     */
    private String packageName;

    /**
     * 模块名
     */
    private String moduleName;

    /**
     * 业务名
     */
    private String bizName;

    /**
     * 类名
     */
    private String className;

    /**
     * 类描述
     */
    private String classDescription;

    /**
     * 父级菜单ID
     */
    private String parentMenuId;

    /**
     * 主键类型
     */
    private GenKeyType keyType;

    /**
     * 作者
     */
    private String author;

    /**
     * 本级菜单编号
     */
    private String menuCode;

    /**
     * 本级菜单名称
     */
    private String menuName;

    /**
     * 详情页Span总数量
     */
    private Integer detailSpan;

    /**
     * 是否应用缓存
     */
    private Boolean isCache;

    /**
     * 是否内置删除功能
     */
    private Boolean hasDelete;
}
