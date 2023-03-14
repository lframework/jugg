package com.lframework.starter.security.bo.system.dic.item;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.mybatis.entity.SysDataDicItem;
import com.lframework.starter.web.bo.BaseBo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysDataDicItemBo extends BaseBo<SysDataDicItem> {

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

  public SysDataDicItemBo() {

  }

  public SysDataDicItemBo(SysDataDicItem dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysDataDicItem dto) {
    this.id = this.code + StringPool.DATA_DIC_SPLIT + this.code;
  }
}
