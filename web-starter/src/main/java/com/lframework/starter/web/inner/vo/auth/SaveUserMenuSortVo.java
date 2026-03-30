package com.lframework.starter.web.inner.vo.auth;

import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 * 保存用户菜单排序请求
 *
 * @author lframework@163.com
 */
@Data
public class SaveUserMenuSortVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  @ApiModelProperty(value = "菜单树", required = true)
  @NotNull(message = "菜单树不能为空！")
  @Valid
  private List<MenuSortNodeVo> menus;

  @Data
  public static class MenuSortNodeVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单ID", required = true)
    @NotBlank(message = "菜单ID不能为空！")
    private String id;

    @ApiModelProperty("子菜单")
    @Valid
    private List<MenuSortNodeVo> children;
  }
}
