package com.lframework.starter.web.inner.bo.system.message.site;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.inner.entity.SysSiteMessage;
import com.lframework.starter.web.inner.entity.SysUser;
import com.lframework.starter.web.inner.service.system.SysUserService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * <p>
 * 站内信 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QuerySysSiteMessageBo extends BaseBo<SysSiteMessage> {

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
   * 接收人姓名
   */
  @Schema(description = "接收人姓名")
  private String receiverName;

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
   * 是否已读
   */
  @Schema(description = "是否已读")
  private Boolean readed;

  /**
   * 已读时间
   */
  @Schema(description = "已读时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime readTime;

  public QuerySysSiteMessageBo() {

  }

  public QuerySysSiteMessageBo(SysSiteMessage dto) {

    super(dto);
  }

  @Override
  protected void afterInit(SysSiteMessage dto) {
    SysUserService sysUserService = ApplicationUtil.getBean(SysUserService.class);
    SysUser receiver = sysUserService.findById(dto.getReceiverId());
    this.receiverName = receiver.getName();
  }
}
