package com.lframework.starter.security.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-07-04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("sys_user_role")
public class DefaultSysUserRole extends BaseEntity {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 用户ID
   */
  private String userId;

  /**
   * 角色ID
   */
  private String roleId;
}
