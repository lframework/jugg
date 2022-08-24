package com.lframework.starter.web.resp;

import com.lframework.common.exceptions.BaseException;

public interface ResponseBuilder {

  /**
   * 是否为默认builder
   *
   * @return
   */
  boolean isDefault();

  /**
   * 当前controller是否匹配
   *
   * @param controller
   * @return
   */
  boolean isMatch(Object controller);

  /**
   * 响应成功-无数据
   *
   * @return
   */
  Response<Void> success();

  /**
   * 响应成功-有数据
   *
   * @param data
   * @return
   */
  <T> Response<T> success(T data);

  /**
   * 响应失败-无信息
   *
   * @return
   */
  Response<Void> fail();

  /**
   * 响应失败-有信息
   *
   * @param msg
   * @return
   */
  Response<Void> fail(String msg);

  /**
   * 响应失败-有信息和数据
   *
   * @param msg
   * @return
   */
  <T> Response<T> fail(String msg, T data);

  /**
   * 响应失败-根据异常
   *
   * @param e
   * @return
   */
  Response<Void> fail(BaseException e);
}
