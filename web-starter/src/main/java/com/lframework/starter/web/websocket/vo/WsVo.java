package com.lframework.starter.web.websocket.vo;

import com.lframework.starter.web.core.vo.BaseVo;
import java.io.Serializable;
import lombok.Data;

@Data
public class WsVo implements BaseVo, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 业务类型
   */
  private String bizType;

  /**
   * 数据
   */
  private String data;
}
