package com.lframework.starter.mybatis.service.message;

import com.lframework.starter.mybatis.resp.PageResult;
import java.io.Serializable;

/**
 * 消息总线Service
 * <p>
 * 注意：一个type只能对应一个Bean
 *
 * @author zmj
 * @since 2022/8/17
 */
public interface MessageBusService<T> {

  /**
   * 获取类型
   *
   * @return
   */
  String getType();

  /**
   * 查询列表
   *
   * @return
   */
  PageResult<T> queryList();

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  T findById(Serializable id);
}
