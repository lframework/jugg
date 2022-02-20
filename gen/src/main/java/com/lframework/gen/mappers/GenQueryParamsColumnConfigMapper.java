package com.lframework.gen.mappers;

import com.lframework.gen.dto.dataobj.GenQueryParamsColumnConfigDto;
import com.lframework.gen.entity.GenQueryParamsColumnConfig;
import com.lframework.starter.mybatis.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-12-10
 */
public interface GenQueryParamsColumnConfigMapper extends BaseMapper<GenQueryParamsColumnConfig> {

    /**
     * 根据数据对象ID查询
     * @param ids
     * @return
     */
    List<GenQueryParamsColumnConfigDto> getByIds(List<String> ids);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    GenQueryParamsColumnConfigDto getById(String id);
}
