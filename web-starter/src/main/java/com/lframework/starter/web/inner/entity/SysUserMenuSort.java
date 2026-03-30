package com.lframework.starter.web.inner.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import lombok.Data;

/**
 * 用户菜单排序偏好
 *
 * @author lframework@163.com
 */
@Data
@TableName("sys_user_menu_sort")
public class SysUserMenuSort extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  private String id;

  private String userId;

  private String menuId;

  private String parentId;

  private Integer sortNo;
}
