package com.lframework.common.exceptions.impl;

import com.lframework.common.constants.ResponseConstants;
import com.lframework.common.exceptions.ClientException;

/**
 * 重复请求异常
 *
 * @author zmj
 */
public class RepeatRequestException extends ClientException {

  public RepeatRequestException() {

    super(ResponseConstants.INVOKE_RESULT_FAIL_CODE_REPEAT_REQUEST,
        ResponseConstants.INVOKE_RESULT_ERROR_MSG_REPEAT_REQUEST);
  }
}
