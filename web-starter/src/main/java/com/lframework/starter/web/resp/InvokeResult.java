package com.lframework.starter.web.resp;

import lombok.Data;

/**
 * 统一响应数据
 *
 * @author zmj
 */
@Data
public class InvokeResult implements Response {

    /**
     * 响应码
     */
    private Integer code;

    /**
     * 响应信息
     */
    private String msg;

    /**
     * 数据
     */
    private Object data;
}
