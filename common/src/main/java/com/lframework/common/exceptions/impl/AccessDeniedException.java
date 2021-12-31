package com.lframework.common.exceptions.impl;

import com.lframework.common.constants.ResponseConstants;
import com.lframework.common.exceptions.ClientException;

/**
 * 由于无权限导致的异常
 *
 * @author zmj
 */
public class AccessDeniedException extends ClientException {

    public AccessDeniedException() {

        super(ResponseConstants.INVOKE_RESULT_FAIL_CODE_ACCESS_DENIED,
                ResponseConstants.INVOKE_RESULT_ERROR_MSG_ACCESS_DENIED);
    }

    public AccessDeniedException(String msg) {

        super(ResponseConstants.INVOKE_RESULT_FAIL_CODE_ACCESS_DENIED, msg);
    }
}
