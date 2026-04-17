package com.lframework.starter.web.inner.vo.qrtz;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * @author zmj
 * @since 2022/8/20
 */
@Data
public class UpdateQrtzVo extends CreateQrtzVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 原始任务名称
   */
  @Schema(description = "原始任务名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "原始任务名称不能为空！")
  private String oriName;

  /**
   * 原始任务分组
   */
  @Schema(description = "原始任务分组", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "原始任务分组不能为空！")
  private String oriGroup;
}
