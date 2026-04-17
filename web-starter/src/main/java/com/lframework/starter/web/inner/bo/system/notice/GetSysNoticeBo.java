package com.lframework.starter.web.inner.bo.system.notice;

import com.lframework.starter.web.inner.entity.SysNotice;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 系统通知 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetSysNoticeBo extends BaseBo<SysNotice> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 标题
   */
  @Schema(description = "标题")
  private String title;

  /**
   * 内容
   */
  @Schema(description = "内容")
  private String content;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Boolean available;

  /**
   * 是否发布
   */
  @Schema(description = "是否发布")
  private Boolean published;

  public GetSysNoticeBo() {

  }

  public GetSysNoticeBo(SysNotice dto) {

    super(dto);
  }

}
