package com.lframework.starter.web.inner.bo.usercenter;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.dto.system.UserInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class UserInfoBo extends BaseBo<UserInfoDto> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 登录名
   */
  @Schema(description = "登录名")
  private String username;

  /**
   * 编号
   */
  @Schema(description = "编号")
  private String code;

  /**
   * 姓名
   */
  @Schema(description = "姓名")
  private String name;

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
  @Schema(description = "性别")
  private Integer gender;

  public UserInfoBo() {

  }

  public UserInfoBo(UserInfoDto dto) {

    super(dto);
  }
}
