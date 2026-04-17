package com.lframework.starter.web.inner.vo.auth;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import java.util.List;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 保存用户菜单排序请求
 *
 * @author lframework@163.com
 */
@Data
public class SaveUserMenuSortVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @Schema(description = "菜单树", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "菜单树不能为空！")
  @Valid
  private List<MenuSortNodeVo> menus;

  @Data
  public static class MenuSortNodeVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    @Schema(description = "菜单ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "菜单ID不能为空！")
    private String id;

    @Schema(description = "子菜单")
    @Valid
    private List<MenuSortNodeVo> children;
  }
}
