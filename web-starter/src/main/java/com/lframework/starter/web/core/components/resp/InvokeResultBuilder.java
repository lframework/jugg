package com.lframework.starter.web.core.components.resp;

import com.lframework.starter.common.constants.ResponseConstants;
import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.web.core.components.trace.TraceBuilder;
import com.lframework.starter.web.core.utils.ApplicationUtil;
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
   *
   * @return
   */
  public static InvokeResult<Void> success() {

    InvokeResult<Void> invokeResult = new InvokeResult<>();
    invokeResult.setCode(ResponseConstants.INVOKE_RESULT_SUCCESS_CODE);
    invokeResult.setMsg(ResponseConstants.INVOKE_RESULT_SUCCESS_MSG);
    invokeResult.setTraceId(ApplicationUtil.getBean(TraceBuilder.class).getTraceId(false));

    return invokeResult;
  }

  /**
   * 响应成功-有数据
   *
   * @param data
   * @return
   */
  public static <T> InvokeResult<T> success(T data) {

    InvokeResult<T> invokeResult = new InvokeResult<>();
    invokeResult.setCode(ResponseConstants.INVOKE_RESULT_SUCCESS_CODE);
    invokeResult.setMsg(ResponseConstants.INVOKE_RESULT_SUCCESS_MSG);
    invokeResult.setData(data);
    invokeResult.setTraceId(ApplicationUtil.getBean(TraceBuilder.class).getTraceId(false));

    return invokeResult;
  }

  /**
   * 响应失败-无信息
   *
   * @return
   */
  public static InvokeResult<Void> fail() {

    InvokeResult<Void> invokeResult = new InvokeResult<>();
    invokeResult.setCode(ResponseConstants.INVOKE_RESULT_FAIL_CODE);
    invokeResult.setMsg(ResponseConstants.INVOKE_RESULT_FAIL_MSG);
    invokeResult.setTraceId(ApplicationUtil.getBean(TraceBuilder.class).getTraceId(false));

    return invokeResult;
  }

  /**
   * 响应失败-有信息
   *
   * @param msg
   * @return
   */
  public static InvokeResult<Void> fail(String msg) {

    InvokeResult<Void> invokeResult = new InvokeResult<>();
    invokeResult.setCode(ResponseConstants.INVOKE_RESULT_FAIL_CODE);
    invokeResult.setMsg(msg);
    invokeResult.setTraceId(ApplicationUtil.getBean(TraceBuilder.class).getTraceId(false));

    return invokeResult;
  }

  /**
   * 响应失败-有信息和数据
   *
   * @param msg
   * @return
   */
  public static <T> InvokeResult<T> fail(String msg, T data) {

    InvokeResult<T> invokeResult = new InvokeResult<>();
    invokeResult.setCode(ResponseConstants.INVOKE_RESULT_FAIL_CODE);
    invokeResult.setMsg(msg);
    invokeResult.setData(data);
    invokeResult.setTraceId(ApplicationUtil.getBean(TraceBuilder.class).getTraceId(false));

    return invokeResult;
  }

  /**
   * 响应失败-根据异常
   *
   * @param e
   * @return
   */
  public static InvokeResult<Void> fail(BaseException e) {

    InvokeResult<Void> invokeResult = new InvokeResult<>();
    invokeResult.setCode(e.getCode());
    invokeResult.setMsg(e.getMsg());
    invokeResult.setTraceId(ApplicationUtil.getBean(TraceBuilder.class).getTraceId(false));

    return invokeResult;
  }
}
