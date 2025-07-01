package com.lframework.starter.web.inner.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.entity.BaseEntity;
import com.lframework.starter.web.inner.enums.system.SysDataPermissionDataBizType;
import lombok.Data;

/**
 * <p>
 * 数据权限数据
 * </p>
 *
 * @author zmj
 */
@Data
@TableName("sys_data_permission_data")
public class SysDataPermissionData extends BaseEntity implements BaseDto {

  /**
   * ID
   */
  private String id;

  /**
   * 业务ID
   */
  private String bizId;

  /**
   * 业务类型
   */
  private SysDataPermissionDataBizType bizType;

  /**
   * 权限类型
   */
  private Integer permissionType;

  /**
   * 权限
   */
  private String permission;
}
