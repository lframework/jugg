package com.lframework.starter.web.inner.vo.system.menu;

import com.lframework.starter.web.inner.enums.system.SysMenuComponentType;
import com.lframework.starter.web.inner.enums.system.SysMenuDisplay;
import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.components.validation.IsEnum;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.v3.oas.annotations.media.Schema;
import java.io.Serializable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 创建菜单Vo
 */
@Data
public class CreateSysMenuVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 租户ID
   */
  @Schema(description = "租户ID", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "租户ID不能为空！")
  private Integer tenantId;

  /**
   * 编号
   */
  @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
  @IsCode
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 标题
   */
  @Schema(description = "标题", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotBlank(message = "请输入标题！")
  private String title;

  /**
   * 图标
   */
  @Schema(description = "图标")
  private String icon;

  /**
   * 类型
   */
  @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED)
  @NotNull(message = "请选择类型！")
  @IsEnum(message = "请选择类型！", enumClass = SysMenuDisplay.class)
  private Integer display;

  /**
   * 父级ID
   */
  @Schema(description = "父级ID")
  private String parentId;

  /**
   * 权限
   */
  @Schema(description = "权限")
  private String permission;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  // 以下均为前端配置项

  /**
   * 路由名称
   */
  @Schema(description = "路由名称")
  private String name;

  /**
   * 组件类型
   */
  @Schema(description = "组件类型")
  @IsEnum(message = "组件类型格式错误！", enumClass = SysMenuComponentType.class)
  private Integer componentType;

  /**
   * 组件
   */
  @Schema(description = "组件")
  private String component;

  /**
   * 自定义请求参数
   */
  @Schema(description = "自定义请求参数")
  private String requestParam;

  /**
   * 路径
   */
  @Schema(description = "路径")
  private String path;

  /**
   * 是否隐藏
   */
  @Schema(description = "是否隐藏")
  private Boolean hidden;

  /**
   * 是否不缓存
   */
  @Schema(description = "是否不缓存")
  private Boolean noCache;
}
