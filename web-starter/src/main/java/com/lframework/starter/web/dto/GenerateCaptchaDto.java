package com.lframework.starter.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 生成验证码Dto
 *
 * @author zmj
 */
@Data
public class GenerateCaptchaDto implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 流水号
   */
  @ApiModelProperty("流水号")
  private String sn;

  /**
   * 图片文件Base64
   */
  @ApiModelProperty("图片文件Base64")
  private String image;
}
