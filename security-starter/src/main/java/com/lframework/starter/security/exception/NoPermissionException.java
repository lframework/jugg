package com.lframework.starter.security.exception;

import org.springframework.security.authentication.AccountStatusException;

/**
 * 无权限异常
 *
 * @author zmj
 */
public class NoPermissionException extends AccountStatusException {

    public NoPermissionException(String msg) {

        super(msg);
    }

    public NoPermissionException(String msg, Throwable t) {

        super(msg, t);
    }
}
