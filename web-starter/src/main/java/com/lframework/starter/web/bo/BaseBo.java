package com.lframework.starter.web.bo;

import com.lframework.common.functions.SFunction;
import com.lframework.common.utils.ArrayUtil;
import com.lframework.common.utils.BeanUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.ReflectUtil;
import com.lframework.starter.web.dto.BaseDto;
import java.io.Serializable;

/**
 * BaseBo
 *
 * @param <T>
 * @author zmj
 */
public abstract class BaseBo<T extends BaseDto> implements Serializable {

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

    if (ObjectUtil.isNull(dto)) {
      return this;
    }

    String[] columnNames = null;
    if (!ArrayUtil.isEmpty(columns)) {
      columnNames = new String[columns.length];
      for (int i = 0; i < columns.length; i++) {
        columnNames[i] = ReflectUtil.getFieldName(columns[i]);
      }
    }
    BeanUtil.copyProperties(dto, this, columnNames);

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
