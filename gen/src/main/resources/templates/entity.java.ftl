package ${packageName}.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
<#if importPackages??>
    <#list importPackages as p>
import ${p};
    </#list>
</#if>
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ${classDescription}
 * </p>
 *
<#if author??>
 * @author ${author}
</#if>
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("${tableName}")
public class ${className} extends BaseEntity {

    private static final long serialVersionUID = 1L;

    <#list columns as column>
    /**
     * ${column.description}
     */
    <#if !column.defaultConvertType>
        <#if !column.isKey>
    @TableField(value = "${column.columnName}"<#if column.fill?? && column.fill>, fill = FieldFill.${column.fillStrategy}</#if>)
        <#else>
    @TableId(value = "${column.columnName}"<#if column.autoIncrKey>, type = IdType.AUTO</#if><#if column.fill?? && column.fill>, fill = FieldFill.${column.fillStrategy}</#if><#if column.autoIncrKey>, type = IdType.AUTO</#if>)
        </#if>
    <#else>
        <#if !column.isKey && column.fill?? && column.fill>
    @TableField(fill = FieldFill.${column.fillStrategy})
        </#if>
    </#if>
    <#if column.defaultConvertType && column.isKey && column.autoIncrKey>
    @TableId(value = "${column.columnName}", type = IdType.AUTO)
    </#if>
    private ${column.type} ${column.name};

    </#list>
}
