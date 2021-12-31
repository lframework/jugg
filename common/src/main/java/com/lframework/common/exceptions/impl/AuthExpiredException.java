package com.lframework.common.exceptions.impl;

import com.lframework.common.constants.ResponseConstants;
import com.lframework.common.exceptions.ClientException;

/**
 * 登录状态过期导致的异常
 *
 * @author zmj
 */
public class AuthExpiredException extends ClientException {

    public AuthExpiredException() {

        super(ResponseConstants.INVOKE_RESULT_FAIL_CODE_AUTH_EXPIRED,
                ResponseConstants.INVOKE_RESULT_ERROR_MSG_AUTH_EXPIRED);
    }
}
