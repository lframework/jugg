package com.lframework.starter.gen.bo.custom.form;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.gen.entity.GenCustomForm;
import com.lframework.starter.gen.entity.GenCustomFormCategory;
import com.lframework.starter.gen.service.GenCustomFormCategoryService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenCustomFormSelectorBo extends BaseBo<GenCustomForm> {

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

  public GenCustomFormSelectorBo() {
  }

  public GenCustomFormSelectorBo(GenCustomForm dto) {
    super(dto);
  }

  @Override
  protected void afterInit(GenCustomForm dto) {
    if (!StringUtil.isBlank(dto.getCategoryId())) {
      GenCustomFormCategoryService genCustomListCategoryService = ApplicationUtil.getBean(
          GenCustomFormCategoryService.class);
      GenCustomFormCategory category = genCustomListCategoryService
          .findById(dto.getCategoryId());
      this.categoryName = category.getName();
    }
  }
}
