package ${packageName}.vo.${moduleName}.${bizName};

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
<#if importPackages??>
    <#list importPackages as p>
import ${p};
    </#list>
</#if>
import java.io.Serializable;

@Data
public class Create${className}Vo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    <#list columns as column>
    /**
     * ${column.description}
     */
    <#if column.required>
    @ApiModelProperty(value = "${column.description}", required = true)
    <#else>
    @ApiModelProperty("${column.description}")
    </#if>
    <#if column.required>
    @${column.validateAnno}(message = "${column.validateMsg}${column.description}！")
    <#if column.fixEnum>
    @IsEnum(message = "${column.validateMsg}${column.description}！", enumClass = ${column.type}.class)
    </#if>
    </#if>
    <#if column.regularExpression??>
    @Pattern(regexp = "${column.regularExpression}", message = "${column.description}格式有误！")
    </#if>
    <#if column.type != 'String'>
    @TypeMismatch(message = "${column.description}格式有误！")
    </#if>
    private <#if column.fixEnum>${column.enumCodeType}<#else>${column.type}</#if> ${column.name};

    </#list>
}
