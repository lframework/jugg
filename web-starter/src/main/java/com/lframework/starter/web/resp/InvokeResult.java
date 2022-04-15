package com.lframework.starter.web.resp;

import com.lframework.common.constants.ResponseConstants;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统一响应数据
 *
 * @author zmj
 */
@Data
public class InvokeResult<T> implements Response<T> {

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
}
