package com.lframework.common.exceptions;

/**
 * 由于客户端请求错误导致的异常
 * 用于表示需要返回信息至前端的异常
 *
 * @author zmj
 */
public abstract class ClientException extends BaseException {

    public ClientException(Integer code, String msg) {

        super(code, msg);
    }
}
