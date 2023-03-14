package com.lframework.starter.common.exceptions.impl;

import com.lframework.starter.common.constants.ResponseConstants;
import com.lframework.starter.common.exceptions.ClientException;

public class UserLoginException extends ClientException {

  public UserLoginException(String msg) {

    super(ResponseConstants.INVOKE_RESULT_FAIL_USER_LOGIN_FAIL, msg);
  }
}
