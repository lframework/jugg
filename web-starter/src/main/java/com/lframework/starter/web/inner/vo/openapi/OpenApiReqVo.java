package com.lframework.starter.web.inner.vo.openapi;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

@Data
public class OpenApiReqVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 客户端ID
   */
  @ApiModelProperty("客户端ID")
  private Integer clientId;

  /**
   * 时间戳
   */
  @ApiModelProperty("时间戳")
  private String timestamp;

  /**
   * 随机数
   */
  @ApiModelProperty("随机数")
  private String nonceStr;

  /**
   * 签名
   */
  @ApiModelProperty("签名")
  private String sign;

  /**
   * 请求参数
   */
  @ApiModelProperty("请求参数")
  private String params;
}
