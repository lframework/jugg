package com.lframework.starter.web.inner.vo.system.notice;

import com.lframework.starter.web.core.components.validation.TypeMismatch;
import com.lframework.starter.web.core.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateSysNoticeVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "id不能为空！")
  private String id;

  /**
   * 标题
   */
  @ApiModelProperty(value = "标题", required = true)
  @NotBlank(message = "请输入标题！")
  private String title;

  /**
   * 内容
   */
  @ApiModelProperty(value = "内容", required = true)
  @NotBlank(message = "请输入内容！")
  private String content;

  /**
   * 状态
   */
  @ApiModelProperty(value = "状态", required = true)
  @TypeMismatch(message = "状态格式有误！")
  @NotNull(message = "请选择状态！")
  private Boolean available;

  /**
   * 是否发布
   */
  @ApiModelProperty(value = "是否发布", required = true)
  @TypeMismatch(message = "是否发布格式有误！")
  @NotNull(message = "请选择是否发布！")
  private Boolean published;
}
