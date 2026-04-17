package com.lframework.starter.web.inner.bo.auth;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.dto.system.LoginDto;
import com.lframework.starter.web.inner.dto.system.LoginDto.UserInfoDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Set;
import lombok.Data;

@Data
public class LoginBo extends BaseBo<LoginDto> {

  /**
   * Token
   */
  @Schema(description = "Token")
  private String token;

  /**
   * 用户信息
   */
  @Schema(description = "用户信息")
  private UserInfoBo user;

  /**
   * 角色
   */
  @Schema(description = "角色")
  private Set<String> roles;

  public LoginBo() {
  }

  public LoginBo(LoginDto dto) {
    super(dto);
  }

  @Override
  protected void afterInit(LoginDto dto) {

    this.user = new UserInfoBo(dto.getUser());
  }

  @Data
  public static class UserInfoBo extends BaseBo<LoginDto.UserInfoDto> {

    private static final long serialVersionUID = 1L;

    /**
     * 姓名
     */
    @Schema(description = "姓名")
    private String name;

    public UserInfoBo() {
    }

    public UserInfoBo(UserInfoDto dto) {
      super(dto);
    }
  }
}
