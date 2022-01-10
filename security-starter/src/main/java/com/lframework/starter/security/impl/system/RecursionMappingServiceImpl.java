package com.lframework.starter.security.impl.system;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.IdUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.security.entity.RecursionMapping;
import com.lframework.starter.security.enums.system.NodeType;
import com.lframework.starter.security.mappers.system.RecursionMappingMapper;
import com.lframework.starter.security.service.system.IRecursionMappingService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

public class RecursionMappingServiceImpl implements IRecursionMappingService {

    @Autowired
    private RecursionMappingMapper recursionMappingMapper;

    @Override
    public List<String> getNodeParentIds(@NonNull String nodeId, @NonNull NodeType nodeType) {

        if (StringUtil.isEmpty(nodeId)) {
            return Collections.EMPTY_LIST;
        }

        Wrapper<RecursionMapping> queryWrapper = Wrappers.lambdaQuery(RecursionMapping.class)
                .eq(RecursionMapping::getNodeId, nodeId).eq(RecursionMapping::getNodeType, nodeType.getCode());

        RecursionMapping recursionMappings = recursionMappingMapper.selectOne(queryWrapper);
        if (recursionMappings == null || StringUtil.isEmpty(recursionMappings.getPath())) {
            return Collections.EMPTY_LIST;
        }

        return StringUtil.split(recursionMappings.getPath(), StringPool.STR_SPLIT);
    }

    @Override
    public List<String> getNodeChildIds(String nodeId, NodeType nodeType) {

        return recursionMappingMapper.getNodeChildIds(nodeId, nodeType.getCode());
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
                .eq(RecursionMapping::getNodeId, nodeId).eq(RecursionMapping::getNodeType, nodeType.getCode());
        recursionMappingMapper.delete(deleteWrapper);
    }

    @Transactional
    @Override
    public void saveNode(@NonNull String nodeId, @NonNull NodeType nodeType, List<String> parentIds) {

        Wrapper<RecursionMapping> deleteWrapper = Wrappers.lambdaQuery(RecursionMapping.class)
                .eq(RecursionMapping::getNodeId, nodeId).eq(RecursionMapping::getNodeType, nodeType.getCode());
        recursionMappingMapper.delete(deleteWrapper);

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

        recursionMappingMapper.insert(data);
    }
}
