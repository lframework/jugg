package com.lframework.starter.web.inner.impl;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.impl.BaseMpServiceImpl;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.core.utils.IdUtil;
import com.lframework.starter.web.inner.entity.RecursionMapping;
import com.lframework.starter.web.inner.enums.system.NodeType;
import com.lframework.starter.web.inner.mappers.RecursionMappingMapper;
import com.lframework.starter.web.inner.service.RecursionMappingService;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RecursionMappingServiceImpl extends
    BaseMpServiceImpl<RecursionMappingMapper, RecursionMapping>
    implements RecursionMappingService {

  @Override
  public List<String> getNodeParentIds(@NonNull String nodeId,
      @NonNull Class<? extends NodeType> nodeTypeClazz) {

    if (StringUtil.isEmpty(nodeId)) {
      return CollectionUtil.emptyList();
    }

    NodeType nodeType = ApplicationUtil.getBean(nodeTypeClazz);
    Wrapper<RecursionMapping> queryWrapper = Wrappers.lambdaQuery(RecursionMapping.class)
        .eq(RecursionMapping::getNodeId, nodeId)
        .eq(RecursionMapping::getNodeType, nodeType.getCode());

    RecursionMapping recursionMappings = getBaseMapper().selectOne(queryWrapper);
    if (recursionMappings == null || StringUtil.isEmpty(recursionMappings.getPath())) {
      return CollectionUtil.emptyList();
    }

    return StringUtil.split(recursionMappings.getPath(), StringPool.STR_SPLIT);
  }

  @Override
  public List<String> getNodeChildIds(String nodeId, Class<? extends NodeType> nodeTypeClazz) {

    NodeType nodeType = ApplicationUtil.getBean(nodeTypeClazz);
    return getBaseMapper().getNodeChildIds(nodeId, nodeType.getCode());
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void saveNode(String nodeId, Class<? extends NodeType> nodeTypeClazz) {

    this.saveNode(nodeId, nodeTypeClazz, null);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteNode(String nodeId, Class<? extends NodeType> nodeTypeClazz) {

    NodeType nodeType = ApplicationUtil.getBean(nodeTypeClazz);

    Wrapper<RecursionMapping> deleteWrapper = Wrappers.lambdaQuery(RecursionMapping.class)
        .eq(RecursionMapping::getNodeId, nodeId)
        .eq(RecursionMapping::getNodeType, nodeType.getCode());
    getBaseMapper().delete(deleteWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void deleteNodeAndChildren(String nodeId, Class<? extends NodeType> nodeTypeClazz) {
    NodeType nodeType = ApplicationUtil.getBean(nodeTypeClazz);
    List<String> childNodeIds = this.getNodeChildIds(nodeId, nodeTypeClazz);
    List<String> allNodeIds = new ArrayList<>();
    allNodeIds.add(nodeId);
    allNodeIds.addAll(childNodeIds);
    Wrapper<RecursionMapping> deleteWrapper = Wrappers.lambdaQuery(RecursionMapping.class)
        .in(RecursionMapping::getNodeId, allNodeIds)
        .eq(RecursionMapping::getNodeType, nodeType.getCode());
    getBaseMapper().delete(deleteWrapper);
  }

  @Transactional(rollbackFor = Exception.class)
  @Override
  public void saveNode(@NonNull String nodeId, @NonNull Class<? extends NodeType> nodeTypeClazz, List<String> parentIds) {

    NodeType nodeType = ApplicationUtil.getBean(nodeTypeClazz);
    Wrapper<RecursionMapping> queryWrapper = Wrappers.lambdaQuery(RecursionMapping.class)
        .eq(RecursionMapping::getNodeId, nodeId)
        .eq(RecursionMapping::getNodeType, nodeType.getCode());
    RecursionMapping data = getBaseMapper().selectOne(queryWrapper);
    if(data == null) {
      data = new RecursionMapping();
      data.setId(IdUtil.getId());
      data.setNodeId(nodeId);
      data.setNodeType(nodeType.getCode());
    }

    data.setLevel(1);
    data.setPath(StringPool.EMPTY_STR);
    if (!CollectionUtil.isEmpty(parentIds)) {
      data.setPath(StringUtil.join(StringPool.STR_SPLIT, parentIds));
      data.setLevel(parentIds.size() + 1);
    }

    getBaseMapper().insert(data);
  }
}
