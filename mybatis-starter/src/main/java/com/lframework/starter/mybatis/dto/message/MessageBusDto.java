package com.lframework.starter.mybatis.dto.message;

import com.lframework.starter.web.dto.BaseDto;

/**
 * @author zmj
 * @since 2022/8/17
 */
public interface MessageBusDto extends BaseDto {

  /**
   * 是否已读
   *
   * @return
   */
  default Boolean getReaded() {
    return false;
  }
}
