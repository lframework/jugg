package com.lframework.gen.vo.gen;

import com.lframework.starter.web.vo.BaseVo;
import java.io.Serializable;
import lombok.Data;

@Data
public class UpdateColumnConfigVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * ID
   */
  private String id;

  /**
   * 是否必填
   */
  private Boolean required;
}
