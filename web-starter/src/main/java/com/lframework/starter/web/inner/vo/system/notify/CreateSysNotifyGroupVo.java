package com.lframework.starter.web.inner.vo.system.notify;

import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.inner.enums.system.SysNotifyReceiverType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSysNotifyGroupVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 名称
   */
  @Schema(description = "名称", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "名称不能为空！")
  private String name;

  /**
   * 接收者类型
   */
  @Schema(description = "接收者类型", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "接收者类型不能为空！")
  @IsEnum(enumClass = SysNotifyReceiverType.class, message = "接收者类型格式不正确！")
  private Integer receiverType;

  /**
   * 接收者ID
   */
  @Schema(description = "接收者ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "接收者ID不能为空！")
  private List<String> receiverIds;

  /**
   * 消息类型
   */
  @Schema(description = "消息类型", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotEmpty(message = "消息类型不能为空！")
  private List<Integer> messageType;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
