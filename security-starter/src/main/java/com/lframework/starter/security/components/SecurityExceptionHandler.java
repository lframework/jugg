package com.lframework.starter.security.components;

import com.lframework.common.exceptions.BaseException;
import com.lframework.common.exceptions.impl.AccessDeniedException;
import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.starter.web.components.WebExceptionHandler;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.resp.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

/**
 * 异常处理器
 *
 * @author zmj
 */
@Slf4j
@RestControllerAdvice
public class SecurityExceptionHandler extends WebExceptionHandler {

    /**
     * 处理无权限异常
     *
     * @param e
     * @return
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Response handle(AccessDeniedException e, HandlerMethod method) {

        this.logException(e, method);

        BaseException ex = new AccessDeniedException();
        this.setResponseCode(ex);

        return InvokeResultBuilder.fail(ex);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public Response badCredentialsException(BadCredentialsException e, HandlerMethod method) {

        this.logException(e, method);

        BaseException ex = new InputErrorException("登录名或密码错误！");

        this.setResponseCode(ex);

        return InvokeResultBuilder.fail(ex);
    }
}
