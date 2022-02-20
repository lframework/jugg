package ${packageName}.dto.${moduleName}.${bizName};

import com.lframework.starter.web.dto.BaseDto;
<#if importPackages??>
    <#list importPackages as p>
import ${p};
    </#list>
</#if>
import lombok.Data;
import java.io.Serializable;

/**
 * <p>
 * ${classDescription} Dto
 * </p>
 *
<#if author??>
 * @author ${author}
</#if>
 */
@Data
public class ${className}Dto implements BaseDto, Serializable {

    private static final long serialVersionUID = 1L;

    public static final String CACHE_NAME = "${className}Dto";

    <#list columns as column>
    /**
     * ${column.description}
     */
    private ${column.type} ${column.name};

    </#list>
}
