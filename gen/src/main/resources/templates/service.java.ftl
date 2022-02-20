package ${packageName}.service.${moduleName};

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.web.service.BaseService;
import ${packageName}.dto.${moduleName}.${bizName}.${className}Dto;
<#if create??>
import ${packageName}.vo.${moduleName}.${bizName}.Create${className}Vo;
</#if>
<#if queryParams??>
import ${packageName}.vo.${moduleName}.${bizName}.Query${className}Vo;
</#if>
<#if update??>
import ${packageName}.vo.${moduleName}.${bizName}.Update${className}Vo;
</#if>
<#if importPackages??>
    <#list importPackages as p>
import ${p};
    </#list>
</#if>
import java.util.Collection;
import java.util.List;

/**
 * ${classDescription} Service
<#if author??>
 * @author ${author}
</#if>
 */
public interface I${className}Service extends BaseService {

    <#if queryParams??>
    /**
     * 查询列表
     * @return
     */
    PageResult${r"<"}${className}Dto${r">"} query(Integer pageIndex, Integer pageSize, Query${className}Vo vo);
    </#if>

    <#if queryParams??>
    /**
     * 查询列表
     * @param vo
     * @return
     */
    ${r"List<"}${className}Dto${r">"} query(Query${className}Vo vo);
    </#if>

    /**
     * 根据ID查询
     * @param ${keys[0].name}
     * @return
     */
    ${className}Dto getById(<#list keys as key>${key.type} ${key.name}<#if key_index != keys?size - 1>, </#if></#list>);

    <#if create??>
    /**
     * 创建
     * @param vo
     * @return
     */
    ${create.keys[0].type} create(Create${className}Vo vo);
    </#if>

    <#if update??>
    /**
     * 修改
     * @param vo
     */
    void update(Update${className}Vo vo);
    </#if>

    <#if hasDelete>
    /**
     * 根据ID删除
     * @param ${keys[0].name}
     * @return
     */
    void deleteById(<#list keys as key>${key.type} ${key.name}<#if key_index != keys?size - 1>, </#if></#list>);
    </#if>
}
