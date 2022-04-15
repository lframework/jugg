package com.lframework.starter.web.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * 菜单Meta Dto
 *
 * @author zmj
 */
@Data
public class MenuMetaDto implements BaseDto, Serializable {

  /**
   * 标题
   */
  @ApiModelProperty("标题")
  private String title;

  /**
   * 是否不缓存
   */
  @ApiModelProperty("是否不缓存")
  private Boolean noCache;
}
