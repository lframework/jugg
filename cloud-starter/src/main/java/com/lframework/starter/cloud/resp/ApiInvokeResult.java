package com.lframework.starter.cloud.resp;

import com.lframework.common.constants.ResponseConstants;
import com.lframework.starter.web.resp.Response;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ApiInvokeResult<T> implements Response<T> {

  private static final long serialVersionUID = 1L;

  /**
   * 响应码
   */
  @ApiModelProperty(value = "响应码，2开头的响应码为成功，否则为失败", example = "200")
  private Integer code;

  /**
   * 响应信息
   */
  @ApiModelProperty(value = "响应信息", example = ResponseConstants.INVOKE_RESULT_SUCCESS_MSG)
  private String msg;

  /**
   * 数据
   */
  @ApiModelProperty("数据")
  private T data;

  /**
   * 来源 子系统名称
   */
  @ApiModelProperty("来源 子系统名称")
  private String source;

  /**
   * 是否响应成功
   *
   * @return
   */
  public boolean success() {
    return code >= 200 & code <= 299;
  }
}
