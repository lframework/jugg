package com.lframework.starter.web.inner.bo.system.dic.item;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.inner.entity.SysDataDic;
import com.lframework.starter.web.inner.entity.SysDataDicItem;
import com.lframework.starter.web.inner.service.system.SysDataDicService;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class SysDataDicItemBo extends BaseBo<SysDataDicItem> {

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

  public SysDataDicItemBo() {

  }

  public SysDataDicItemBo(SysDataDicItem dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysDataDicItem dto) {
    SysDataDicService sysDataDicService = ApplicationUtil.getBean(SysDataDicService.class);
    SysDataDic dataDic = sysDataDicService.findById(dto.getDicId());
    this.id = dataDic.getCode() + StringPool.DATA_DIC_SPLIT + this.code;
  }
}
