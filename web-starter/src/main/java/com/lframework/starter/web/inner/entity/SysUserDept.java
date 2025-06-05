package com.lframework.starter.web.inner.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import lombok.Data;

/**
 * <p>
 *
 * </p>
 *
 * @author zmj
 * @since 2021-07-04
 */
@Data
@TableName("sys_user_dept")
public class SysUserDept extends BaseEntity implements BaseDto {

  private static final long serialVersionUID = 1L;

  public static final String CACHE_NAME = "SysUserDept";

  /**
   * ID
   */
  private String id;

  /**
   * 用户ID
   */
  private String userId;

  /**
   * 部门ID
   */
  private String deptId;
}
