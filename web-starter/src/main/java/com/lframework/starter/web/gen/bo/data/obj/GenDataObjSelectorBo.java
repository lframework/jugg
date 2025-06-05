package com.lframework.starter.web.gen.bo.data.obj;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.gen.entity.GenDataObj;
import com.lframework.starter.web.gen.entity.GenDataObjCategory;
import com.lframework.starter.web.gen.service.GenDataObjCategoryService;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GenDataObjSelectorBo extends BaseBo<GenDataObj> {

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

  public GenDataObjSelectorBo() {
  }

  public GenDataObjSelectorBo(GenDataObj dto) {
    super(dto);
  }

  @Override
  protected void afterInit(GenDataObj dto) {
    if (!StringUtil.isBlank(dto.getCategoryId())) {
      GenDataObjCategoryService genDataObjCategoryService = ApplicationUtil.getBean(
          GenDataObjCategoryService.class);
      GenDataObjCategory category = genDataObjCategoryService.findById(dto.getCategoryId());
      this.categoryName = category.getName();
    }
  }
}
