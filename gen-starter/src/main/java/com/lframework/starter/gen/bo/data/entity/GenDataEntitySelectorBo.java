package com.lframework.starter.gen.bo.data.entity;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.gen.entity.GenDataEntity;
import com.lframework.starter.gen.entity.GenDataEntityCategory;
import com.lframework.starter.gen.service.IGenDataEntityCategoryService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenDataEntitySelectorBo extends BaseBo<GenDataEntity> {

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
   * 分类名称
   */
  @ApiModelProperty("分类名称")
  private String categoryName;

  public GenDataEntitySelectorBo() {
  }

  public GenDataEntitySelectorBo(GenDataEntity dto) {
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
  }
}
