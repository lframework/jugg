package com.lframework.starter.security.bo.system.dic;

import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.mybatis.entity.SysDataDic;
import com.lframework.starter.mybatis.entity.SysDataDicCategory;
import com.lframework.starter.mybatis.service.system.SysDataDicCategoryService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.common.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSysDataDicBo extends BaseBo<SysDataDic> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 名称
   */
  @ApiModelProperty("名称")
  private String name;

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


  public GetSysDataDicBo() {

  }

  public GetSysDataDicBo(SysDataDic dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysDataDic dto) {
    if (!StringUtil.isBlank(dto.getCategoryId())) {
      SysDataDicCategoryService sysDataDicCategoryService = ApplicationUtil.getBean(
          SysDataDicCategoryService.class);
      SysDataDicCategory category = sysDataDicCategoryService.findById(dto.getCategoryId());
      this.categoryName = category.getName();
    }
  }
}
