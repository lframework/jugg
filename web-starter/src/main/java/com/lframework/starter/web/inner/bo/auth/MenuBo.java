package com.lframework.starter.web.inner.bo.auth;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.dto.VoidDto;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import lombok.Data;

@Data
public class MenuBo extends BaseBo<VoidDto> {

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 组件
   */
  @Schema(description = "组件")
  private String component;

  /**
   * 子节点
   */
  @Schema(description = "子节点")
  private List<MenuBo> children;

  /**
   * 路由路径
   */
  @Schema(description = "路由路径")
  private String path;

  /**
   * 元数据
   */
  @Schema(description = "元数据")
  private MetaBo meta;

  public MenuBo() {
  }

  @Data
  public static class MetaBo extends BaseBo<VoidDto> {

    /**
     * ID
     */
    @Schema(description = "ID")
    private String id;

    /**
     * 标题
     */
    @Schema(description = "标题")
    private String title;

    /**
     * 图标
     */
    @Schema(description = "图标")
    private String icon;

    /**
     * 是否隐藏
     */
    @Schema(description = "是否隐藏")
    private Boolean hideMenu;

    /**
     * 是否不缓存
     */
    @Schema(description = "是否不缓存")
    private Boolean ignoreKeepAlive;

    /**
     * 是否固定
     */
    @Schema(description = "是否固定")
    private Boolean affix = Boolean.FALSE;

    /**
     * 是否外部链接
     */
    @Schema(description = "是否外部链接")
    private Boolean isLink = Boolean.FALSE;

    /**
     * 是否收藏
     */
    @Schema(description = "是否收藏")
    private Boolean isCollect = Boolean.FALSE;

    /**
     * 内嵌链接地址
     */
    @Schema(description = "内嵌链接地址")
    private String frameSrc;

    /**
     * 自定义列表ID
     */
    @Schema(description = "自定义列表ID")
    private String customListId;

    /**
     * 自定义页面ID
     */
    @Schema(description = "自定义页面ID")
    private String customPageId;
  }
}
