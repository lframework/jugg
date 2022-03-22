package com.lframework.starter.security.vo.system.dept;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSysDeptVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 编号
   */
  @NotBlank(message = "编号不能为空！")
  private String code;

  /**
   * 名称
   */
  @NotBlank(message = "名称不能为空！")
  private String name;

  /**
   * 简称
   */
  @NotBlank(message = "简称不能为空！")
  private String shortName;

  /**
   * 父级ID
   */
  private String parentId;

  /**
   * 状态
   */
  @NotNull(message = "请选择状态！")
  private Boolean available;

  /**
   * 备注
   */
  private String description;
}
