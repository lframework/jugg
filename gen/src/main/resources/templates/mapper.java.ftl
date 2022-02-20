package ${packageName}.mappers;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import ${packageName}.entity.${className};
<#if queryParams??>
import ${packageName}.vo.${moduleName}.${bizName}.Query${className}Vo;
</#if>
import ${packageName}.dto.${moduleName}.${bizName}.${className}Dto;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * ${classDescription} Mapper 接口
 * </p>
 *
<#if author??>
 * @author ${author}
</#if>
 */
public interface ${className}Mapper extends BaseMapper<${className}> {

    <#if queryParams??>
	/**
     * 查询列表
     * @param vo
     * @return
     */
    List${r"<"}${className}Dto${r">"} query(@Param("vo") Query${className}Vo vo);
    </#if>

    /**
     * 根据ID查询
     */
    ${className}Dto getById(<#list keys as key>@Param("${key.name}") ${key.type} ${key.name}<#if key_index != keys?size - 1>, </#if></#list>);
}
