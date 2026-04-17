package com.lframework.starter.web.inner.vo.system.notice;

import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.core.vo.PageVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class QuerySysNoticeVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 标题
   */
  @Schema(description = "标题")
  private String title;

  /**
   * 创建时间 起始时间
   */
  @Schema(description = "创建时间 起始时间")
  @TypeMismatch(message = "创建时间起始时间格式有误！")
  private LocalDateTime createTimeStart;

  /**
   * 创建时间 截止时间
   */
  @Schema(description = "创建时间 截止时间")
  @TypeMismatch(message = "创建时间截止时间格式有误！")
  private LocalDateTime createTimeEnd;

  /**
   * 状态
   */
  @Schema(description = "状态")
  private Boolean available;

}
