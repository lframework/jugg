package com.lframework.starter.web.inner.vo.system.dept;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class SysUserDeptSettingVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 用户ID
   */
  @NotEmpty(message = "用户ID不能为空！")
  private List<String> userIds;

  /**
   * 部门ID
   */
  private List<String> deptIds;

  /**
   * 处理方式 1：新增 2：替换 3：删除
   */
  @Schema(description = "处理方式", requiredMode = Schema.RequiredMode.REQUIRED)
  private Integer handleType;
}
