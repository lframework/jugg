package com.lframework.starter.web.gen.bo.custom.page;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.gen.entity.GenCustomPage;
import com.lframework.starter.web.gen.entity.GenCustomPageCategory;
import com.lframework.starter.web.gen.service.GenCustomPageCategoryService;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenCustomPageSelectorBo extends BaseBo<GenCustomPage> {

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

  public GenCustomPageSelectorBo() {
  }

  public GenCustomPageSelectorBo(GenCustomPage dto) {
    super(dto);
  }

  @Override
  protected void afterInit(GenCustomPage dto) {
    if (!StringUtil.isBlank(dto.getCategoryId())) {
      GenCustomPageCategoryService genCustomPageCategoryService = ApplicationUtil.getBean(
          GenCustomPageCategoryService.class);
      GenCustomPageCategory category = genCustomPageCategoryService
          .findById(dto.getCategoryId());
      this.categoryName = category.getName();
    }
  }
}
