package com.lframework.starter.security.service.system;


import com.lframework.starter.security.enums.system.NodeType;

import java.util.List;

public interface IRecursionMappingService {

    /**
     * 查询节点的父节点ID
     * @param nodeId
     * @param nodeType
     * @return
     */
    List<String> getNodeParentIds(String nodeId, NodeType nodeType);

    /**
     * 查询节点的子节点ID
     * @param nodeId
     * @param nodeType
     * @return
     */
    List<String> getNodeChildIds(String nodeId, NodeType nodeType);


    /**
     * 保存节点递归信息
     * @param nodeId
     * @param nodeType
     * @param parentIds 顺序有意义，需要从顶点到终点的顺序排列
     */
    void saveNode(String nodeId, NodeType nodeType, List<String> parentIds);

    /**
     * 保存节点递归信息
     * @param nodeId
     * @param nodeType
     */
    void saveNode(String nodeId, NodeType nodeType);

    /**
     * 根据节点ID删除
     * @param nodeId
     * @param nodeType
     */
    void deleteNode(String nodeId, NodeType nodeType);
}
