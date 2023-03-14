package com.lframework.starter.common.exceptions.impl;

import com.lframework.starter.common.constants.ResponseConstants;
import com.lframework.starter.common.exceptions.SysException;

/**
 * 自定义消息的系统异常
 *
 * @author zmj
 */
public class DefaultSysException extends SysException {

  public DefaultSysException() {

    super(ResponseConstants.INVOKE_RESULT_FAIL_CODE, ResponseConstants.INVOKE_RESULT_ERROR_MSG);
  }

  public DefaultSysException(String msg) {

    super(ResponseConstants.INVOKE_RESULT_FAIL_CODE, msg);
  }
}
