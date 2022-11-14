package com.lframework.starter.gen.vo.custom.list;

import com.lframework.starter.web.vo.BaseVo;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateGenCustomListVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty(value = "ID", required = true)
  @NotBlank(message = "ID不能为空！")
  private String id;

  /**
   * 名称
   */
  @ApiModelProperty(value = "名称", required = true)
  @NotBlank(message = "名称不能为空！")
  private String name;

  /**
   * 分类ID
   */
  @ApiModelProperty("分类ID")
  private String categoryId;

  /**
   * 表单Label宽度
   */
  @ApiModelProperty(value = "表单Label宽度", required = true)
  @NotNull(message = "表单Label宽度不能为空！")
  @Min(value = 1, message = "表单Label宽度不允许小于0！")
  private Integer labelWidth;

  /**
   * 默认值
   */
  @ApiModelProperty("默认值")
  private String defaultValue;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 状态
   */
  @ApiModelProperty(value = "状态", required = true)
  @NotNull(message = "状态不能为空！")
  private Boolean available;

  /**
   * 查询条件
   */
  @ApiModelProperty("查询条件")
  @Valid
  private List<GenCustomListQueryParamsVo> queryParams;

  /**
   * 列表配置
   */
  @ApiModelProperty(value = "列表配置", required = true)
  @NotEmpty(message = "列表配置不能为空！")
  @Valid
  private List<GenCustomLisDetailVo> details;

  /**
   * 查询前置SQL
   */
  @ApiModelProperty("查询前置SQL")
  private String queryPrefixSql;

  /**
   * 查询后置SQL
   */
  @ApiModelProperty("查询后置SQL")
  private String querySuffixSql;

  /**
   * 后置SQL
   */
  @ApiModelProperty("后置SQL")
  private String suffixSql;
}
