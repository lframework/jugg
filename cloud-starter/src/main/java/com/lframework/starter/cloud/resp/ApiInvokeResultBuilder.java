package com.lframework.starter.cloud.resp;

import com.lframework.starter.common.exceptions.BaseException;
import com.lframework.starter.cloud.constants.ResponseConstants;
import com.lframework.starter.web.core.utils.ApplicationUtil;

public class ApiInvokeResultBuilder {

  /**
   * 响应成功-无数据
   *
   * @return
   */
  public static ApiInvokeResult<Void> success() {

    ApiInvokeResult<Void> apiInvokeResult = new ApiInvokeResult<>();
    apiInvokeResult.setCode(ResponseConstants.API_INVOKE_RESULT_SUCCESS_CODE);
    apiInvokeResult.setMsg(ResponseConstants.API_INVOKE_RESULT_SUCCESS_MSG);
    apiInvokeResult.setSource(ApplicationUtil.getProperty("spring.application.name"));

    return apiInvokeResult;
  }

  /**
   * 响应成功-有数据
   *
   * @param data
   * @return
   */
  public static <T> ApiInvokeResult<T> success(T data) {

    ApiInvokeResult<T> apiInvokeResult = new ApiInvokeResult<>();
    apiInvokeResult.setCode(ResponseConstants.API_INVOKE_RESULT_SUCCESS_CODE);
    apiInvokeResult.setMsg(ResponseConstants.API_INVOKE_RESULT_SUCCESS_MSG);
    apiInvokeResult.setData(data);
    apiInvokeResult.setSource(ApplicationUtil.getProperty("spring.application.name"));

    return apiInvokeResult;
  }

  /**
   * 响应失败-无信息
   *
   * @return
   */
  public static ApiInvokeResult<Void> fail() {

    ApiInvokeResult<Void> apiInvokeResult = new ApiInvokeResult<>();
    apiInvokeResult.setCode(ResponseConstants.API_INVOKE_RESULT_FAIL_CODE);
    apiInvokeResult.setMsg(ResponseConstants.API_INVOKE_RESULT_FAIL_MSG);
    apiInvokeResult.setSource(ApplicationUtil.getProperty("spring.application.name"));

    return apiInvokeResult;
  }

  /**
   * 响应失败-有信息
   *
   * @param msg
   * @return
   */
  public static ApiInvokeResult<Void> fail(String msg) {

    ApiInvokeResult<Void> apiInvokeResult = new ApiInvokeResult<>();
    apiInvokeResult.setCode(ResponseConstants.API_INVOKE_RESULT_FAIL_CODE);
    apiInvokeResult.setMsg(msg);
    apiInvokeResult.setSource(ApplicationUtil.getProperty("spring.application.name"));

    return apiInvokeResult;
  }

  /**
   * 响应失败-根据异常
   *
   * @param e
   * @return
   */
  public static ApiInvokeResult<Void> fail(BaseException e) {

    ApiInvokeResult<Void> apiInvokeResult = new ApiInvokeResult<>();
    apiInvokeResult.setCode(e.getCode());
    apiInvokeResult.setMsg(e.getMsg());
    apiInvokeResult.setSource(ApplicationUtil.getProperty("spring.application.name"));
    apiInvokeResult.setExClass(e.getClass().getName());

    return apiInvokeResult;
  }
}
