package ${packageName}.vo.${moduleName}.${bizName};

import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import lombok.Data;
import lombok.EqualsAndHashCode;
<#if importPackages??>
    <#list importPackages as p>
import ${p};
    </#list>
</#if>
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class Query${className}Vo extends PageVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    <#list columns as column>
    <#if column.viewType != 6>
    /**
     * ${column.description}
     */
    @ApiModelProperty("${column.description}")
     </#if>
    <#if column.fixEnum>
    <#if column.type != 'String'>
    @TypeMismatch(message = "${column.description}格式有误！")
    </#if>
    <#if column.regularExpression??>
    @Pattern(regexp = "${column.regularExpression}", message = "${column.description}格式有误！")
    </#if>
    @IsEnum(message = "${column.description}格式有误！", enumClass = ${column.type}.class)
    private ${column.enumCodeType} ${column.name};
    <#else>
    <#if column.viewType == 6>
    /**
     * ${column.description} 起始时间
     */
    @ApiModelProperty("${column.description} 起始时间")
    <#if column.type != 'String'>
    @TypeMismatch(message = "${column.description}起始时间格式有误！")
    </#if>
    <#if column.regularExpression??>
    @Pattern(regexp = "${column.regularExpression}", message = "${column.description}格式有误！")
    </#if>
    private ${column.type} ${column.name}Start;

    /**
     * ${column.description} 截止时间
     */
    @ApiModelProperty("${column.description} 截止时间")
    <#if column.type != 'String'>
    @TypeMismatch(message = "${column.description}截止时间格式有误！")
    </#if>
    <#if column.regularExpression??>
    @Pattern(regexp = "${column.regularExpression}", message = "${column.description}格式有误！")
    </#if>
    private ${column.type} ${column.name}End;
    <#else>
    <#if column.type != 'String'>
    @TypeMismatch(message = "${column.description}格式有误！")
    </#if>
    <#if column.regularExpression??>
    @Pattern(regexp = "${column.regularExpression}", message = "${column.description}格式有误！")
    </#if>
    private ${column.type} ${column.name};
    </#if>
    </#if>

    </#list>
}
