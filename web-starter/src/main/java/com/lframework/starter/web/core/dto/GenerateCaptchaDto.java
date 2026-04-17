package com.lframework.starter.web.core.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
  @Schema(description = "流水号")
  private String sn;

  /**
   * 图片文件Base64
   */
  @Schema(description = "图片文件Base64")
  private String image;
}
