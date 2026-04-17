package com.lframework.starter.web.inner.vo.qrtz;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.inner.enums.system.QrtzJobType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * @author zmj
 * @since 2022/8/20
 */
@Data
public class CreateQrtzVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "名称不能为空！")
  private String name;

  /**
   * 分组
   */
  @Schema(description = "分组", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "分组不能为空！")
  private String group;

  /**
   * 租户ID
   */
  @Schema(description = "租户ID")
  private Integer tenantId;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  /**
   * 执行类名
   */
  @Schema(description = "执行类名")
  private String targetClassName;

  /**
   * 执行方法名
   */
  @Schema(description = "执行方法名")
  private String targetMethodName;

  /**
   * 执行参数类型
   */
  @Schema(description = "执行参数类型")
  private List<String> targetParamTypes;

  /**
   * 执行参数
   */
  @Schema(description = "执行参数")
  private List<String> targetParams;

  /**
   * Cron表达式
   */
  @Schema(description = "Cron表达式")
  @NotBlank(message = "Cron表达式不能为空！")
  private String cron;

  /**
   * 任务类型
   */
  @NotNull(message = "任务类型不能为空！")
  @IsEnum(message = "任务类型不能为空！", enumClass = QrtzJobType.class)
  private Integer jobType;

  /**
   * 脚本
   */
  @Schema(description = "脚本")
  private String script;
}
