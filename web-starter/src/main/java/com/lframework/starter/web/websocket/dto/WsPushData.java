package com.lframework.starter.web.websocket.dto;

import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.utils.JsonUtil;
import java.io.Serializable;
import java.util.List;
import lombok.Data;

/**
 * WS推送数据
 */
@Data
public class WsPushData implements BaseDto, Serializable {

  private static final long serialVersionUID = 1L;

  /**
   * 业务类型
   */
  private String bizType;

  /**
   * 是否全员广播
   */
  private Boolean all = Boolean.FALSE;

  /**
   * 接收用户ID集合 all=true时，此值无效
   */
  private List<String> includeUserIds;

  /**
   * 接收用户ID all=true时，此值无效
   */
  private String includeUserId;

  /**
   * 租户ID
   */
  private Integer tenantId;

  /**
   * 指定SessionId集合
   */
  private List<String> includeSessionIds;

  /**
   * 需要排除的SessionId
   */
  private List<String> excludeSessionIds;

  /**
   * 推送数据
   */
  private String data;

  public void setDataObj(Object obj) {
    if (obj == null) {
      this.data = null;
    } else if (obj instanceof CharSequence) {
      this.data = obj.toString();
    } else {
      this.data = JsonUtil.toJsonString(obj);
    }
  }
}
