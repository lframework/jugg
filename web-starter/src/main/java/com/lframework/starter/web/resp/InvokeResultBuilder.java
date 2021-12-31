package com.lframework.starter.web.resp;

import com.lframework.common.constants.ResponseConstants;
import com.lframework.common.exceptions.BaseException;
import lombok.extern.slf4j.Slf4j;

/**
 * 统一响应数据Builder
 *
 * @author zmj
 */
@Slf4j
public class InvokeResultBuilder {

    /**
     * 响应成功-无数据
     * @return
     */
    public static InvokeResult success() {

        InvokeResult invokeResult = new InvokeResult();
        invokeResult.setCode(ResponseConstants.INVOKE_RESULT_SUCCESS_CODE);
        invokeResult.setMsg(ResponseConstants.INVOKE_RESULT_SUCCESS_MSG);

        return invokeResult;
    }

    /**
     * 响应成功-有数据
     * @param data
     * @return
     */
    public static InvokeResult success(Object data) {

        InvokeResult invokeResult = new InvokeResult();
        invokeResult.setCode(ResponseConstants.INVOKE_RESULT_SUCCESS_CODE);
        invokeResult.setMsg(ResponseConstants.INVOKE_RESULT_SUCCESS_MSG);
        invokeResult.setData(data);

        return invokeResult;
    }

    /**
     * 响应失败-无信息
     * @return
     */
    public static InvokeResult fail() {

        InvokeResult invokeResult = new InvokeResult();
        invokeResult.setCode(ResponseConstants.INVOKE_RESULT_FAIL_CODE);
        invokeResult.setMsg(ResponseConstants.INVOKE_RESULT_FAIL_MSG);

        return invokeResult;
    }

    /**
     * 响应失败-有信息
     * @param msg
     * @return
     */
    public static InvokeResult fail(String msg) {

        InvokeResult invokeResult = new InvokeResult();
        invokeResult.setCode(ResponseConstants.INVOKE_RESULT_FAIL_CODE);
        invokeResult.setMsg(msg);

        return invokeResult;
    }

    /**
     * 响应失败-根据异常
     * @param e
     * @return
     */
    public static InvokeResult fail(BaseException e) {

        InvokeResult invokeResult = new InvokeResult();
        invokeResult.setCode(e.getCode());
        invokeResult.setMsg(e.getMsg());

        return invokeResult;
    }
}
