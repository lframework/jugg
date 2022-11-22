package com.lframework.starter.gen.vo.custom.list;

import com.lframework.starter.gen.enums.GenCustomListType;
import com.lframework.starter.web.components.validation.IsEnum;
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
public class CreateGenCustomListVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

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
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 列表类型
   */
  @ApiModelProperty(value = "列表类型", required = true)
  @NotNull(message = "列表类型不能为空！")
  @IsEnum(message = "列表类型不能为空！", enumClass = GenCustomListType.class)
  private Integer listType;

  /**
   * 数据对象ID
   */
  @ApiModelProperty(value = "数据对象ID", required = true)
  @NotBlank(message = "数据对象ID不能为空！")
  private String dataObjId;

  /**
   * 表单Label宽度
   */
  @ApiModelProperty(value = "表单Label宽度", required = true)
  @NotNull(message = "表单Label宽度不能为空！")
  @Min(value = 1, message = "表单Label宽度不允许小于0！")
  private Integer labelWidth;

  /**
   * 是否分页
   */
  @ApiModelProperty(value = "是否分页", required = true)
  @NotNull(message = "是否分页不能为空！")
  private Boolean hasPage;

  /**
   * 是否树形列表
   */
  @ApiModelProperty(value = "是否树形列表", required = true)
  @NotNull(message = "是否树形列表不能为空！")
  private Boolean treeData;

  /**
   * ID字段
   */
  @ApiModelProperty(value = "ID字段", required = true)
  @NotBlank(message = "ID字段不能为空！")
  private String idColumn;

  /**
   * ID字段关联ID
   */
  @ApiModelProperty(value = "ID字段关联ID", required = true)
  @NotBlank(message = "ID字段不能为空！")
  private String idColumnRelaId;

  /**
   * 父级ID字段
   */
  @ApiModelProperty("父级ID字段")
  private String treePidColumn;

  /**
   * 父级ID字段关联ID
   */
  @ApiModelProperty("父级ID字段关联ID")
  private String treePidColumnRelaId;

  /**
   * 树形节点字段
   */
  @ApiModelProperty("树形节点字段")
  private String treeNodeColumn;

  /**
   * 树形节点字段关联ID
   */
  @ApiModelProperty("树形节点字段关联ID")
  private String treeNodeColumnRelaId;

  /**
   * 子节点Key值
   */
  @ApiModelProperty("子节点Key值")
  private String treeChildrenKey;

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
