package com.lframework.starter.mybatis.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.entity.RecursionMapping;
import com.lframework.starter.mybatis.enums.system.NodeType;
import com.lframework.starter.mybatis.impl.BaseMpServiceImpl;
import com.lframework.starter.mybatis.mappers.system.RecursionMappingMapper;
import com.lframework.starter.mybatis.service.system.IRecursionMappingService;
import com.lframework.starter.web.utils.IdUtil;
import java.util.Collections;
import java.util.List;
import lombok.NonNull;
import org.springframework.transaction.annotation.Transactional;

public class RecursionMappingServiceImpl extends
    BaseMpServiceImpl<RecursionMappingMapper, RecursionMapping>
    implements IRecursionMappingService {

  @Override
  public List<String> getNodeParentIds(@NonNull String nodeId, @NonNull NodeType nodeType) {

    if (StringUtil.isEmpty(nodeId)) {
      return Collections.EMPTY_LIST;
    }

    Wrapper<RecursionMapping> queryWrapper = Wrappers.lambdaQuery(RecursionMapping.class)
        .eq(RecursionMapping::getNodeId, nodeId)
        .eq(RecursionMapping::getNodeType, nodeType.getCode());

    RecursionMapping recursionMappings = getBaseMapper().selectOne(queryWrapper);
    if (recursionMappings == null || StringUtil.isEmpty(recursionMappings.getPath())) {
      return Collections.EMPTY_LIST;
    }

    return StringUtil.split(recursionMappings.getPath(), StringPool.STR_SPLIT);
  }

  @Override
  public List<String> getNodeChildIds(String nodeId, NodeType nodeType) {

    return getBaseMapper().getNodeChildIds(nodeId, nodeType.getCode());
  }

  @Transactional
  @Override
  public void saveNode(String nodeId, NodeType nodeType) {

    this.saveNode(nodeId, nodeType, null);
  }

  @Transactional
  @Override
  public void deleteNode(String nodeId, NodeType nodeType) {

    Wrapper<RecursionMapping> deleteWrapper = Wrappers.lambdaQuery(RecursionMapping.class)
        .eq(RecursionMapping::getNodeId, nodeId)
        .eq(RecursionMapping::getNodeType, nodeType.getCode());
    getBaseMapper().delete(deleteWrapper);
  }

  @Transactional
  @Override
  public void saveNode(@NonNull String nodeId, @NonNull NodeType nodeType, List<String> parentIds) {

    Wrapper<RecursionMapping> deleteWrapper = Wrappers.lambdaQuery(RecursionMapping.class)
        .eq(RecursionMapping::getNodeId, nodeId)
        .eq(RecursionMapping::getNodeType, nodeType.getCode());
    getBaseMapper().delete(deleteWrapper);

    RecursionMapping data = new RecursionMapping();
    data.setId(IdUtil.getId());
    data.setNodeId(nodeId);
    data.setNodeType(nodeType.getCode());
    data.setLevel(1);
    data.setPath(StringPool.EMPTY_STR);
    if (!CollectionUtil.isEmpty(parentIds)) {
      data.setPath(StringUtil.join(StringPool.STR_SPLIT, parentIds));
      data.setLevel(parentIds.size() + 1);
    }

    getBaseMapper().insert(data);
  }
}
