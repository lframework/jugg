package com.lframework.starter.security.mappers.system;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.security.entity.RecursionMapping;
import com.lframework.starter.security.enums.system.NodeType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  递归映射 Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-06-27
 */
public interface RecursionMappingMapper extends BaseMapper<RecursionMapping> {

    List<String> getNodeChildIds(@Param("nodeId") String nodeId, @Param("nodeType") NodeType nodeType);
}
