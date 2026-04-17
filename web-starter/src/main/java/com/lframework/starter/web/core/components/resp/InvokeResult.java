package com.lframework.starter.web.core.components.resp;

import com.lframework.starter.common.constants.ResponseConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 统一响应数据
 *
 * @author zmj
 */
@Data
public class InvokeResult<T> implements Response<T> {

  private static final long serialVersionUID = 1L;

  /**
   * 响应码
   */
  @Schema(description = "响应码，2开头的响应码为成功，否则为失败", example = "200")
  private Integer code;

  /**
   * 响应信息
   */
  @Schema(description = "响应信息", example = ResponseConstants.INVOKE_RESULT_SUCCESS_MSG)
  private String msg;

  /**
   * 数据
   */
  @Schema(description = "数据")
  private T data;

  /**
   * TraceId
   */
  @Schema(description = "TraceId")
  private String traceId;
}
