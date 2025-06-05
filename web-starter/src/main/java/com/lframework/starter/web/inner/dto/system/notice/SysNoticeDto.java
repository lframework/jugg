package com.lframework.starter.web.inner.dto.system.notice;

import com.lframework.starter.web.inner.entity.SysNotice;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.dto.BaseDto;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import lombok.Data;

/**
 * @author zmj
 * @since 2022/8/18
 */
@Data
public class SysNoticeDto extends BaseBo<SysNotice> implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 标题
   */
  @ApiModelProperty("标题")
  private String title;

  /**
   * 内容
   */
  @ApiModelProperty("内容")
  private String content;

  public SysNoticeDto(SysNotice dto) {
    super(dto);
  }
}
