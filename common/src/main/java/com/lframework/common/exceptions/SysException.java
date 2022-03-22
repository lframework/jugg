package com.lframework.common.exceptions;

/**
 * 由于系统内部错误导致的异常 用于表示程序运行错误或其他情况导致的不能将错误信息返回前端的异常
 *
 * @author zmj
 */
public abstract class SysException extends BaseException {

  public SysException(Integer code, String msg) {

    super(code, msg);
  }
}
