package com.lframework.starter.web.inner.vo.system.user;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.inner.enums.system.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSysUserVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 编号
   */
  @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
  @IsCode
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 姓名
   */
  @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入姓名！")
  private String name;

  /**
   * 用户名
   */
  @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入用户名！")
  private String username;

  /**
   * 密码 如果不为空则为修改密码
   */
  @Schema(description = "密码 如果不为空则为修改密码")
  private String password;

  /**
   * 邮箱
   */
  @Schema(description = "邮箱")
  private String email;

  /**
   * 联系电话
   */
  @Schema(description = "联系电话")
  private String telephone;

  /**
   * 性别
   */
  @Schema(description = "性别", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择性别！")
  @IsEnum(message = "请选择性别！", enumClass = Gender.class)
  private Integer gender;

  /**
   * 角色ID
   */
  @Schema(description = "角色ID")
  private List<String> roleIds;

  /**
   * 部门ID
   */
  @Schema(description = "部门ID")
  private List<String> deptIds;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
