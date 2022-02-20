package com.lframework.starter.mybatis.mapper;

import com.baomidou.mybatisplus.core.conditions.AbstractWrapper;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.lframework.common.exceptions.impl.DefaultSysException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * BaseMapper 所有Mapper的基类
 * @param <T>
 *
 * @author zmj
 */
public interface BaseMapper<T> extends com.baomidou.mybatisplus.core.mapper.BaseMapper<T> {

    Logger LOGGER = LoggerFactory.getLogger(BaseMapper.class);

    /**
     * 根据 whereEntity 条件，更新记录
     * @param updateWrapper 实体对象封装操作类（可以为 null,里面的 entity 用于生成 where 语句）
     * @return
     */
    default int update(Wrapper<T> updateWrapper) {

        T entity = null;
        try {
            if (updateWrapper instanceof AbstractWrapper) {
                entity = (T) ((AbstractWrapper) updateWrapper).getEntityClass().newInstance();
            }
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new DefaultSysException(e.getMessage());
        }

        return update(entity, updateWrapper);
    }
}
