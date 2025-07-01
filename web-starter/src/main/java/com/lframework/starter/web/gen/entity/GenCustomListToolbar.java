package com.lframework.starter.web.gen.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.gen.enums.GenCustomListBtnType;
import com.lframework.starter.web.gen.enums.GenCustomListBtnViewType;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.starter.web.core.dto.BaseDto;
import lombok.Data;

/**
 * <p>
 * 自定义列表工具栏
 * </p>
 *
 * @author zmj
 * @since 2022-09-24
 */
@Data
@TableName("gen_custom_list_toolbar")
public class GenCustomListToolbar extends BaseEntity implements BaseDto {

  public static final String CACHE_NAME = "GenCustomListToolbar";

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 自定义列表ID
   */
  private String customListId;

  /**
   * 显示名称
   */
  private String name;

  /**
   * 显示类型
   */
  private GenCustomListBtnViewType viewType;

  /**
   * 按钮类型
   */
  private GenCustomListBtnType btnType;

  /**
   * 按钮配置
   */
  private String btnConfig;

  /**
   * 图标
   */
  private String icon;

  /**
   * 请求参数
   */
  private String requestParam;

  /**
   * 排序编号
   */
  private Integer orderNo;
}
