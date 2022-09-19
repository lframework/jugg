package com.lframework.starter.security.bo.system.dic.item;

import com.lframework.starter.mybatis.entity.SysDataDicItem;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySysDataDicItemBo extends BaseBo<SysDataDicItem> {

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
   * 字典ID
   */
  @ApiModelProperty("字典ID")
  private String dicId;

  /**
   * 排序
   */
  @ApiModelProperty("排序")
  private Integer orderNo;


  public QuerySysDataDicItemBo() {

  }

  public QuerySysDataDicItemBo(SysDataDicItem dto) {

    super(dto);
  }
}
