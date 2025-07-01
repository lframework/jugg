package com.lframework.starter.web.inner.vo.system.dept;

import com.lframework.starter.web.core.components.validation.IsCode;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateSysDeptVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 编号
   */
  @ApiModelProperty(value = "编号", required = true)
  @IsCode
  @NotBlank(message = "编号不能为空！")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "名称不能为空！")
  private String name;

  /**
   * 简称
   */
  @ApiModelProperty(value = "简称", required = true)
  @NotBlank(message = "简称不能为空！")
  private String shortName;

  /**
   * 父级ID
   */
  @ApiModelProperty("父级ID")
  private String parentId;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;
}
