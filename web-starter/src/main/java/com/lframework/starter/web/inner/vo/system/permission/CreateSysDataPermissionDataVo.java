package com.lframework.starter.web.inner.vo.system.permission;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.inner.enums.system.SysDataPermissionDataBizType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSysDataPermissionDataVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 业务ID
   */
  @Schema(description = "业务ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "业务ID不能为空！")
  private List<String> bizIds;

  /**
   * 业务类型
   */
  @Schema(description = "业务类型", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "业务类型不能为空！")
  @IsEnum(message = "业务类型格式错误！", enumClass = SysDataPermissionDataBizType.class)
  private Integer bizType;

  /**
   * 权限类型
   */
  @Schema(description = "权限类型", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "权限类型不能为空！")
  private Integer permissionType;

  /**
   * 权限
   */
  @Schema(description = "权限")
  private String permission;
}
