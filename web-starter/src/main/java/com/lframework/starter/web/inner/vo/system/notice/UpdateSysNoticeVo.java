package com.lframework.starter.web.inner.vo.system.notice;

import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSysNoticeVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "id不能为空！")
  private String id;

  /**
   * 标题
   */
  @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入标题！")
  private String title;

  /**
   * 内容
   */
  @Schema(description = "内容", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入内容！")
  private String content;

  /**
   * 状态
   */
  @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
  @TypeMismatch(message = "状态格式有误！")
  @NotNull(message = "请选择状态！")
  private Boolean available;

  /**
   * 是否发布
   */
  @Schema(description = "是否发布", requiredMode = Schema.RequiredMode.REQUIRED)
  @TypeMismatch(message = "是否发布格式有误！")
  @NotNull(message = "请选择是否发布！")
  private Boolean published;
}
