package com.lframework.starter.web.inner.vo.system.role;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSysRoleVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @ApiModelProperty(value = "编号", required = true)
  @IsCode
  @NotBlank(message = "请输入编号！")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "请输入名称！")
  private String name;

  /**
   * 分类ID
   */
  @ApiModelProperty(value = "分类ID", required = true)
  @NotBlank(message = "请选择分类！")
  private String categoryId;

  /**
   * 权限
   */
  @ApiModelProperty("权限")
  private String permission;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}
