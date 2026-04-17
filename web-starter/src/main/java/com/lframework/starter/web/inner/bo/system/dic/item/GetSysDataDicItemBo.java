package com.lframework.starter.web.inner.bo.system.dic.item;

import com.lframework.starter.web.inner.entity.SysDataDicItem;
import com.lframework.starter.web.core.bo.BaseBo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetSysDataDicItemBo extends BaseBo<SysDataDicItem> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 编号
   */
  @Schema(description = "编号")
  private String code;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 字典ID
   */
  @Schema(description = "字典ID")
  private String dicId;

  /**
   * 排序
   */
  @Schema(description = "排序")
  private Integer orderNo;


  public GetSysDataDicItemBo() {

  }

  public GetSysDataDicItemBo(SysDataDicItem dto) {

    super(dto);
  }
}
