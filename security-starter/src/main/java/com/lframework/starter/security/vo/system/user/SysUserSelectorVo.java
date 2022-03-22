package com.lframework.starter.security.vo.system.user;

import com.lframework.starter.web.vo.BaseVo;
import com.lframework.starter.web.vo.PageVo;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysUserSelectorVo extends PageVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  private String code;

  /**
   * 用户名
   */
  private String username;

  /**
   * 姓名
   */
  private String name;

  /**
   * 状态
   */
  private Boolean available;
}
