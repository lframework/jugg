package com.lframework.starter.web.bo;

import com.lframework.starter.web.dto.BaseDto;
import lombok.Data;

@Data
public class BasePrintDataBo<T extends BaseDto> extends BaseBo<T> {

  public BasePrintDataBo() {

  }

  public BasePrintDataBo(T dto) {

    super(dto);
  }
}
