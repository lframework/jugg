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
public class Update${className}Vo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ${keys[0].description}
     */
<#if keys[0].type == 'String'>
    @NotBlank(message = "${keys[0].name}不能为空！")
<#else>
    @NotNull(message = "${keys[0].name}不能为空！")
</#if>
    private ${keys[0].type} ${keys[0].name};

<#list columns as column>
    /**
     * ${column.description}
     */
    <#if column.type != 'String'>
    @TypeMismatch(message = "${column.description}格式有误！")
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
    private <#if column.fixEnum>${column.enumCodeType}<#else>${column.type}</#if> ${column.name};

</#list>
}
