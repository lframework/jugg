package com.lframework.gen.mappers;

import com.lframework.gen.dto.dataobj.GenCreateColumnConfigDto;
import com.lframework.gen.entity.GenCreateColumnConfig;
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
public interface GenCreateColumnConfigMapper extends BaseMapper<GenCreateColumnConfig> {

    /**
     * 根据数据对象ID查询
     * @param ids
     * @return
     */
    List<GenCreateColumnConfigDto> getByIds(List<String> ids);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    GenCreateColumnConfigDto getById(String id);
}
