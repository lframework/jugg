package com.lframework.starter.common.exceptions.impl;

import com.lframework.starter.common.constants.ResponseConstants;
import com.lframework.starter.common.exceptions.ClientException;

/**
 * 自定义消息的异常
 *
 * @author zmj
 */
public class DefaultClientException extends ClientException {

  public DefaultClientException(String msg) {

    super(ResponseConstants.INVOKE_RESULT_FAIL_CODE, msg);
  }
}
