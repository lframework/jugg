package com.lframework.starter.web.dto;

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
  private String title;

  /**
   * 是否缓存
   */
  private Boolean noCache;
}
