package com.lframework.starter.security.vo.system.position;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSysPositionVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 岗位编号
   */
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 岗位名称
   */
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 备注
   */
  private String description;
}
