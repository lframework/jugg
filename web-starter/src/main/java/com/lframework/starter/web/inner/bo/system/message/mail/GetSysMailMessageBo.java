package com.lframework.starter.web.inner.bo.system.message.mail;

import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.SysMailMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * <p>
 * 邮件消息 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetSysMailMessageBo extends BaseBo<SysMailMessage> {

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

  public GetSysMailMessageBo() {

  }

  public GetSysMailMessageBo(SysMailMessage dto) {

    super(dto);
  }

}
