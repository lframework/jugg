package com.lframework.starter.web.inner.service;


import com.lframework.starter.web.core.service.BaseMpService;
import com.lframework.starter.web.inner.entity.RecursionMapping;
import com.lframework.starter.web.inner.enums.system.NodeType;
import java.util.List;

public interface RecursionMappingService extends BaseMpService<RecursionMapping> {

  /**
   * 查询节点的父节点ID
   *
   * @param nodeId
   * @param nodeTypeClazz
   * @return
   */
  List<String> getNodeParentIds(String nodeId, Class<? extends NodeType> nodeTypeClazz);

  /**
   * 查询节点的子节点ID
   *
   * @param nodeId
   * @param nodeTypeClazz
   * @return
   */
  List<String> getNodeChildIds(String nodeId, Class<? extends NodeType> nodeTypeClazz);


  /**
   * 保存节点递归信息
   *
   * @param nodeId
   * @param nodeTypeClazz
   * @param parentIds 顺序有意义，需要从顶点到终点的顺序排列
   */
  void saveNode(String nodeId, Class<? extends NodeType> nodeTypeClazz, List<String> parentIds);

  /**
   * 保存节点递归信息
   *
   * @param nodeId
   * @param nodeTypeClazz
   */
  void saveNode(String nodeId, Class<? extends NodeType> nodeTypeClazz);

  /**
   * 根据节点ID删除
   *
   * @param nodeId
   * @param nodeTypeClazz
   */
  void deleteNode(String nodeId, Class<? extends NodeType> nodeTypeClazz);

  /**
   * 根据节点ID删除以及子节点
   *
   * @param nodeId
   * @param nodeTypeClazz
   */
  void deleteNodeAndChildren(String nodeId, Class<? extends NodeType> nodeTypeClazz);
}
