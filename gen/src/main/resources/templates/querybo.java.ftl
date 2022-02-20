package ${packageName}.bo.${moduleName}.${bizName};

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.web.bo.BaseBo;
import ${packageName}.dto.${moduleName}.${bizName}.${className}Dto;
<#if importPackages??>
    <#list importPackages as p>
import ${p};
    </#list>
</#if>

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * ${classDescription} QueryBo
 * </p>
 *
<#if author??>
 * @author ${author}
</#if>
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Query${className}Bo extends BaseBo${r"<"}${className}Dto${r">"} {

    /**
     * ${keys[0].description}
     */
    private ${keys[0].type} ${keys[0].name};

<#list columns as column>
    /**
     * ${column.description}
     */
    <#if column.type == 'LocalDateTime'>
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    </#if>
    <#if column.type == 'LocalDate'>
    @JsonFormat(pattern = StringPool.DATE_PATTERN)
    </#if>
    <#if column.type == 'LocalTime'>
    @JsonFormat(pattern = StringPool.TIME_PATTERN)
    </#if>
    private <#if column.fixEnum>${column.enumCodeType}<#else>${column.type}</#if> ${column.name};

</#list>
    public Query${className}Bo() {

    }

    public Query${className}Bo(${className}Dto dto) {

        super(dto);
    }

<#if hasFixEnum>
    @Override
    public BaseBo${r"<"}${className}Dto${r">"} convert(${className}Dto dto) {

        return super.convert(dto<#list columns as column><#if column.fixEnum>, Query${className}Bo::get${column.nameProperty}</#if></#list>);
    }

    @Override
    protected void afterInit(${className}Dto dto) {

    <#list columns as column>
        <#if column.fixEnum>
        this.${column.name} = dto.get${column.nameProperty}().getCode();
        </#if>
    </#list>
    }
</#if>
}
