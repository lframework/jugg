package com.lframework.starter.web.inner.vo.system.user;

import com.lframework.starter.common.constants.PatternPool;
import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.components.validation.Regex;
import com.lframework.starter.web.core.vo.BaseVo;
import com.lframework.starter.web.inner.enums.system.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreateSysUserVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @IsCode
  @NotBlank(message = "请输入编号！")
  @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
  private String code;

  /**
   * 姓名
   */
  @NotBlank(message = "请输入姓名！")
  @Schema(description = "姓名", requiredMode = Schema.RequiredMode.REQUIRED)
  private String name;

  /**
   * 用户名
   */
  @NotBlank(message = "请输入用户名！")
  @Schema(description = "用户名", requiredMode = Schema.RequiredMode.REQUIRED)
  private String username;

  /**
   * 密码
   */
  @Regex(regexp = PatternPool.PATTERN_STR_PASSWORD, message = "密码长度必须为5-16位，只允许包含大写字母、小写字母、数字、下划线")
  @NotBlank(message = "请输入密码！")
  @Schema(description = "密码", requiredMode = Schema.RequiredMode.REQUIRED)
  private String password;

  /**
   * 邮箱
   */
  @Regex(regexp = PatternPool.PATTERN_STR_EMAIL, message = "邮箱地址格式不正确！")
  @Schema(description = "邮箱")
  private String email;

  /**
   * 联系电话
   */
  @Regex(regexp = PatternPool.PATTERN_STR_CN_TEL, message = "联系电话格式不正确！")
  @Schema(description = "联系电话")
  private String telephone;

  /**
   * 性别 0-未知 1-男 2-女
   */
  @NotNull(message = "请选择性别！")
  @IsEnum(message = "请选择性别！", enumClass = Gender.class)
  @Schema(description = "性别")
  private Integer gender;

  /**
   * 部门ID
   */
  @Schema(description = "部门ID")
  private List<String> deptIds;

  /**
   * 角色ID
   */
  @Schema(description = "角色ID")
  private List<String> roleIds;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;
}
