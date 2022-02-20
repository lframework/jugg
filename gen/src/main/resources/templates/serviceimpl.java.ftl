package ${packageName}.impl.${moduleName};

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.Assert;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.mybatis.annotations.OpLog;
import com.lframework.starter.mybatis.enums.OpLogType;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.OpLogUtil;
import com.lframework.starter.mybatis.utils.PageHelperUtil;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.utils.EnumUtil;
import ${packageName}.dto.${moduleName}.${bizName}.${className}Dto;
import ${packageName}.entity.${className};
<#if importPackages??>
    <#list importPackages as p>
import ${p};
    </#list>
</#if>
import ${packageName}.mappers.${className}Mapper;
import ${packageName}.service.${moduleName}.I${className}Service;
<#if create??>
import ${packageName}.vo.${moduleName}.${bizName}.Create${className}Vo;
</#if>
<#if queryParams??>
import ${packageName}.vo.${moduleName}.${bizName}.Query${className}Vo;
</#if>
<#if update??>
import ${packageName}.vo.${moduleName}.${bizName}.Update${className}Vo;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

@Service
public class ${className}ServiceImpl implements I${className}Service {

    @Autowired
    private ${className}Mapper ${classNameProperty}Mapper;
<#if queryParams??>

    @Override
    public PageResult${r"<"}${className}Dto${r">"} query(Integer pageIndex, Integer pageSize, Query${className}Vo vo) {

        Assert.greaterThanZero(pageIndex);
        Assert.greaterThanZero(pageSize);

        PageHelperUtil.startPage(pageIndex, pageSize);
        List${r"<"}${className}Dto${r">"} datas = this.query(vo);

        return PageResultUtil.convert(new PageInfo<>(datas));
    }
</#if>
<#if queryParams??>

    @Override
    public List${r"<"}${className}Dto${r">"} query(Query${className}Vo vo) {

        return ${classNameProperty}Mapper.query(vo);
    }
</#if>

    <#if isCache>
    @Cacheable(value = ${className}Dto.CACHE_NAME, key = "<#list keys as key>#${key.name}<#if key_index != keys?size - 1>_</#if></#list>", unless = "#result == null")
    </#if>
    @Override
    public ${className}Dto getById(<#list keys as key>${key.type} ${key.name}<#if key_index != keys?size - 1>, </#if></#list>) {

        return ${classNameProperty}Mapper.getById(<#list keys as key>${key.name}<#if key_index != keys?size - 1>, </#if></#list>);
    }
<#if create??>

    @OpLog(type = OpLogType.OTHER, name = "新增${classDescription}，ID：{}", params = ${r'{"#'}${create.keys[0].name}${r'"}'})
    @Transactional
    @Override
    public ${create.keys[0].type} create(Create${className}Vo vo) {

        ${className} data = new ${className}();
        <#if create.appointId>
        data.set${create.keys[0].nameProperty}(${create.idCode});
        </#if>
        <#list create.columns as column>
            <#if column.required>
                <#if column.fixEnum>
        data.set${column.nameProperty}(EnumUtil.getByCode(${column.type}.class, vo.get${column.nameProperty}()));
                <#else>
        data.set${column.nameProperty}(vo.get${column.nameProperty}());
                </#if>
            <#else>
                <#if column.type == 'String'>
        if (!StringUtil.isBlank(vo.get${column.nameProperty}())) {
            data.set${column.nameProperty}(vo.get${column.nameProperty}());
        }
                <#else>
        if (vo.get${column.nameProperty}() != null) {
                    <#if column.fixEnum>
            data.set${column.nameProperty}(EnumUtil.getByCode(${column.type}.class, vo.get${column.nameProperty}()));
                    <#else>
            data.set${column.nameProperty}(vo.get${column.nameProperty}());
                    </#if>
        }
                </#if>
            </#if>
        </#list>

        ${classNameProperty}Mapper.insert(data);

        OpLogUtil.setVariable("${create.keys[0].name}", <#if create.keys[0].type == 'String'>data.get${create.keys[0].nameProperty}()<#else>String.valueOf(data.get${create.keys[0].nameProperty}())</#if>);
        OpLogUtil.setExtra(vo);

        return data.get${create.keys[0].nameProperty}();
    }
</#if>
<#if update??>

    @OpLog(type = OpLogType.OTHER, name = "修改${classDescription}，ID：{}", params = ${r'{"#'}${update.keys[0].name}${r'"}'})
    @Transactional
    @Override
    public void update(Update${className}Vo vo) {

        ${className} data = ${classNameProperty}Mapper.selectById(vo.get${update.keys[0].nameProperty}());
        if (ObjectUtil.isNull(data)) {
            throw new DefaultClientException("${classDescription}不存在！");
        }

        LambdaUpdateWrapper${r"<"}${className}${r">"} updateWrapper = Wrappers.lambdaUpdate(${className}.class)
    <#list update.columns as column>
        <#if column.required>
            <#if column.fixEnum>
                .set(${className}::get${column.nameProperty}, EnumUtil.getByCode(${column.type}.class, vo.get${column.nameProperty}()))
            <#else>
                .set(${className}::get${column.nameProperty}, vo.get${column.nameProperty}())
            </#if>
        <#else>
            <#if column.type == 'String'>
                .set(${className}::get${column.nameProperty}, StringUtil.isBlank(vo.get${column.nameProperty}()) ? null : vo.get${column.nameProperty}())
            <#else>
                <#if column.fixEnum>
                .set(${className}::get${column.nameProperty}, vo.get${column.nameProperty}() == null ? null : EnumUtil.getByCode(${column.type}.class, vo.get${column.nameProperty}()))
                <#else>
                .set(${className}::get${column.nameProperty}, vo.get${column.nameProperty}() == null ? null : vo.get${column.nameProperty}())
                </#if>
            </#if>
        </#if>
    </#list>
                .eq(${className}::get${update.keys[0].nameProperty}, vo.get${update.keys[0].nameProperty}());

        ${classNameProperty}Mapper.update(updateWrapper);

        OpLogUtil.setVariable("${update.keys[0].name}", <#if create.keys[0].type == 'String'>data.get${create.keys[0].nameProperty}()<#else>String.valueOf(data.get${create.keys[0].nameProperty}())</#if>);
        OpLogUtil.setExtra(vo);
        <#if isCache>

        I${className}Service thisService = getThis(this.getClass());
        thisService.cleanCacheByKey(<#if create.keys[0].type == 'String'>data.get${create.keys[0].nameProperty}()<#else>String.valueOf(data.get${create.keys[0].nameProperty}())</#if>);

        </#if>
    }
</#if>
    <#if hasDelete>

    @OpLog(type = OpLogType.OTHER, name = "删除${classDescription}，ID：{}", params = ${r'{"#'}${keys[0].name}${r'"}'})
    @Transactional
    @Override
    public void deleteById(<#list keys as key>${key.type} ${key.name}<#if key_index != keys?size - 1>, </#if></#list>) {

        ${classNameProperty}Mapper.deleteById(<#list keys as key>${key.name}<#if key_index != keys?size - 1>, </#if></#list>);
        <#if isCache>

        I${className}Service thisService = getThis(this.getClass());
        thisService.cleanCacheByKey(<#if keys[0].type == 'String'>${keys[0].name}<#else>String.valueOf(${keys[0].name})</#if>);
        </#if>
    }
    </#if>

    <#if isCache>
    @CacheEvict(value = ${className}Dto.CACHE_NAME, key = "#key")
    </#if>
    @Override
    public void cleanCacheByKey(String key) {

    }
}
