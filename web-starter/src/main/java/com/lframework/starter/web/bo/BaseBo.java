package com.lframework.starter.web.bo;

import com.lframework.starter.common.functions.SFunction;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.starter.web.utils.BoUtil;
import java.io.Serializable;

/**
 * BaseBo
 *
 * @param <T>
 * @author zmj
 */
public abstract class BaseBo<T extends BaseDto> implements Serializable, SuperBo {

  private static final long serialVersionUID = 1L;

  public BaseBo() {

  }

  public BaseBo(T dto) {

    if (dto != null) {
      this.convert(dto);

      this.afterInit(dto);
    }
  }

  /**
   * 将dto转为bo
   *
   * @param dto
   */
  public <A> BaseBo<T> convert(T dto) {

    return convert(dto, (SFunction<A, ?>[]) null);
  }

  public <A> BaseBo<T> convert(T dto, SFunction<A, ?>... columns) {

    BoUtil.convert(dto, this, columns);

    return this;
  }

  /**
   * 初始化后置处理
   *
   * @param dto
   */
  protected void afterInit(T dto) {

  }
}
