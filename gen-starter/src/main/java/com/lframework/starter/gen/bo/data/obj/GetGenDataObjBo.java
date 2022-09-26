package com.lframework.starter.gen.bo.data.obj;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.gen.entity.GenDataEntity;
import com.lframework.starter.gen.entity.GenDataObj;
import com.lframework.starter.gen.entity.GenDataObjCategory;
import com.lframework.starter.gen.entity.GenDataObjDetail;
import com.lframework.starter.gen.entity.GenDataObjQueryDetail;
import com.lframework.starter.gen.service.IGenDataEntityService;
import com.lframework.starter.gen.service.IGenDataObjCategoryService;
import com.lframework.starter.gen.service.IGenDataObjDetailService;
import com.lframework.starter.gen.service.IGenDataObjQueryDetailService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetGenDataObjBo extends BaseBo<GenDataObj> {

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
   * 主表ID
   */
  @ApiModelProperty("主表ID")
  private String mainTableId;

  /**
   * 主表名称
   */
  @ApiModelProperty("主表名称")
  private String mainTableName;

  /**
   * 主表别名
   */
  @ApiModelProperty("主表别名")
  private String mainTableAlias;

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
   * 关联字段
   */
  @ApiModelProperty("关联字段")
  private List<GenDataObjDetailBo> columns;

  /**
   * 自定义查询
   */
  @ApiModelProperty("自定义查询")
  private List<GenDataObjQueryDetailBo> queryColumns;

  public GetGenDataObjBo() {

  }

  public GetGenDataObjBo(GenDataObj dto) {

    super(dto);
  }

  @Override
  protected void afterInit(GenDataObj dto) {

    if (!StringUtil.isBlank(dto.getCategoryId())) {
      IGenDataObjCategoryService genDataObjCategoryService = ApplicationUtil.getBean(
          IGenDataObjCategoryService.class);
      GenDataObjCategory category = genDataObjCategoryService.findById(dto.getCategoryId());
      this.categoryName = category.getName();
    }

    IGenDataEntityService genDataEntityService = ApplicationUtil.getBean(
        IGenDataEntityService.class);
    GenDataEntity entity = genDataEntityService.findById(dto.getMainTableId());
    this.mainTableName = entity.getName();

    IGenDataObjDetailService genDataObjDetailService = ApplicationUtil.getBean(
        IGenDataObjDetailService.class);
    List<GenDataObjDetail> details = genDataObjDetailService.getByObjId(dto.getId());
    if (!CollectionUtil.isEmpty(details)) {
      this.columns = details.stream().map(GenDataObjDetailBo::new).collect(Collectors.toList());
    }

    IGenDataObjQueryDetailService genDataObjQueryDetailService = ApplicationUtil.getBean(
        IGenDataObjQueryDetailService.class);
    List<GenDataObjQueryDetail> queryDetails = genDataObjQueryDetailService.getByObjId(dto.getId());
    if (!CollectionUtil.isEmpty(queryDetails)) {
      this.queryColumns = queryDetails.stream().map(GenDataObjQueryDetailBo::new)
          .collect(Collectors.toList());
    }
  }
}
