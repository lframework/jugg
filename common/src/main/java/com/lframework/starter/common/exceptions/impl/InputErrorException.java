package com.lframework.starter.common.exceptions.impl;

import com.lframework.starter.common.constants.ResponseConstants;
import com.lframework.starter.common.exceptions.ClientException;

/**
 * 由于客户端传入参数错误导致的异常
 *
 * @author zmj
 */
public class InputErrorException extends ClientException {

  public InputErrorException() {

    super(ResponseConstants.INVOKE_RESULT_FAIL_CODE_INPUT_ERROR,
        ResponseConstants.INVOKE_RESULT_ERROR_MSG_INPUT_ERROR);
  }

  public InputErrorException(String msg) {

    super(ResponseConstants.INVOKE_RESULT_FAIL_CODE_INPUT_ERROR, msg);
  }
}
