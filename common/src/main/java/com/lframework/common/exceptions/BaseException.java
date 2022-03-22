package com.lframework.common.exceptions;

/**
 * 系统内异常基类
 *
 * @author zmj
 */
public abstract class BaseException extends RuntimeException {

  /**
   * 响应码
   */
  private Integer code;

  /**
   * 响应信息
   */
  private String msg;

  public BaseException(Integer code, String msg) {

    super(msg);

    this.code = code;
    this.msg = msg;
  }

  public Integer getCode() {

    return code;
  }

  public String getMsg() {

    return msg;
  }
}
