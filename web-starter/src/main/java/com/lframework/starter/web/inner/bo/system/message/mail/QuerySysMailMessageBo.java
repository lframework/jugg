package com.lframework.starter.web.inner.bo.system.message.mail;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.annotations.constants.EncryType;
import com.lframework.starter.web.core.annotations.convert.EncryptConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.inner.entity.SysMailMessage;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 邮件消息 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QuerySysMailMessageBo extends BaseBo<SysMailMessage> {

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
   * 接收邮箱
   */
  @Schema(description = "接收邮箱")
  @EncryptConvert(type = EncryType.EMAIL)
  private String mail;

  /**
   * 创建人
   */
  @Schema(description = "创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @Schema(description = "创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 发送状态
   */
  @Schema(description = "发送状态")
  private Integer sendStatus;

  public QuerySysMailMessageBo() {

  }

  public QuerySysMailMessageBo(SysMailMessage dto) {

    super(dto);
  }
}
