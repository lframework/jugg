package com.lframework.starter.gen.bo.data.entity;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.gen.entity.GenDataEntity;
import com.lframework.starter.gen.entity.GenDataEntityCategory;
import com.lframework.starter.gen.entity.GenDataEntityDetail;
import com.lframework.starter.gen.service.IGenDataEntityCategoryService;
import com.lframework.starter.gen.service.IGenDataEntityDetailService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetDataEntityBo extends BaseBo<GenDataEntity> {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

  /**
   * 数据表
   */
  @ApiModelProperty("数据表")
  private String tableName;

  /**
   * 分类ID
   */
  @ApiModelProperty("分类ID")
  private String categoryId;

  /**
   * 分类名称
   */
  @ApiModelProperty("分类名称")
  private String categoryName;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 字段信息
   */
  @ApiModelProperty("字段信息")
  private List<GenDataEntityDetailBo> columns;

  public GetDataEntityBo() {

  }

  public GetDataEntityBo(GenDataEntity dto) {

    super(dto);
  }

  @Override
  protected void afterInit(GenDataEntity dto) {

    if (!StringUtil.isBlank(dto.getCategoryId())) {
      IGenDataEntityCategoryService genDataEntityCategoryService = ApplicationUtil.getBean(
          IGenDataEntityCategoryService.class);
      GenDataEntityCategory category = genDataEntityCategoryService.findById(dto.getCategoryId());
      this.categoryName = category.getName();
    }

    this.tableName = dto.getTableName();

    IGenDataEntityDetailService genDataEntityDetailService = ApplicationUtil.getBean(
        IGenDataEntityDetailService.class);
    List<GenDataEntityDetail> details = genDataEntityDetailService.getByEntityId(dto.getId());
    this.columns = details.stream().map(GenDataEntityDetailBo::new).collect(Collectors.toList());
  }
}
