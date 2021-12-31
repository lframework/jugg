package com.lframework.common.constants;

/**
 * 响应常量
 *
 * @author zmj
 */
public class ResponseConstants {

    /**
     * 响应成功状态码
     */
    public static final Integer INVOKE_RESULT_SUCCESS_CODE = 200;

    /**
     * 响应失败状态码-传入参数错误
     */
    public static final Integer INVOKE_RESULT_FAIL_CODE_INPUT_ERROR = 400;

    /**
     * 响应失败状态码-无登录状态
     */
    public static final Integer INVOKE_RESULT_FAIL_CODE_AUTH_EXPIRED = 401;

    /**
     * 响应失败状态码-无权限
     */
    public static final Integer INVOKE_RESULT_FAIL_CODE_ACCESS_DENIED = 403;

    /**
     * 响应失败状态码-重复请求
     */
    public static final Integer INVOKE_RESULT_FAIL_CODE_REPEAT_REQUEST = 410;

    /**
     * 响应失败状态码
     */
    public static final Integer INVOKE_RESULT_FAIL_CODE = 500;

    /**
     * 响应成功标识
     */
    public static final String INVOKE_RESULT_SUCCESS_MSG = "success";

    /**
     * 响应失败标识
     */
    public static final String INVOKE_RESULT_FAIL_MSG = "fail";

    /**
     * 响应成功信息
     */
    public static final String INVOKE_RESULT_ERROR_MSG = "系统出现内部错误，请联系系统管理员！";

    /**
     * 响应失败信息-传入参数错误
     */
    public static final String INVOKE_RESULT_ERROR_MSG_INPUT_ERROR = "传入参数有误！";

    /**
     * 响应失败信息-无登录状态
     */
    public static final String INVOKE_RESULT_ERROR_MSG_AUTH_EXPIRED = "请重新登录！";

    /**
     * 响应失败信息-无权限
     */
    public static final String INVOKE_RESULT_ERROR_MSG_ACCESS_DENIED = "无系统权限！";

    /**
     * 响应失败信息-重复请求
     */
    public static final String INVOKE_RESULT_ERROR_MSG_REPEAT_REQUEST = "请求过于频繁，请稍后再试！";
}
