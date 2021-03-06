package com.lframework.starter.mybatis.dto.system.dept;

import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;
import lombok.Data;

@Data
public class DefaultSysUserDeptDto implements BaseDto, Serializable {

  public static final String CACHE_NAME = "DefaultSysUserDeptDto";
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
   * 部门ID
   */
  private String deptId;
}
