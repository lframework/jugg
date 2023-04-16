package com.lframework.starter.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.lframework.starter.mybatis.enums.system.SysDataPermissionDataBizType;
import com.lframework.starter.mybatis.enums.system.SysDataPermissionDataPermissionType;
import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 数据权限数据
 * </p>
 *
 * @author zmj
 */
@Data
@EqualsAndHashCode(callSuper = true)
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
  private SysDataPermissionDataPermissionType permissionType;

  /**
   * 权限
   */
  private String permission;
}
