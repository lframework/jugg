package com.lframework.starter.web.inner.bo.system.message.site;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.SysSiteMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 站内信 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetSysSiteMessageBo extends BaseBo<SysSiteMessage> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 标题
   */
  @Schema(description = "标题")
  private String title;

  /**
   * 内容
   */
  @Schema(description = "内容")
  private String content;

  public GetSysSiteMessageBo() {

  }

  public GetSysSiteMessageBo(SysSiteMessage dto) {

    super(dto);
  }

}
