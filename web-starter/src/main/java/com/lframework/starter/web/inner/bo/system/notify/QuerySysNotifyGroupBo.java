package com.lframework.starter.web.inner.bo.system.notify;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.EnumUtil;
import com.lframework.starter.web.inner.entity.SysNotifyGroup;
import com.lframework.starter.web.inner.enums.system.SysNotifyMessageType;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * <p>
 * 消息通知组 QueryBo
 * </p>
 *
 * @author zmj
 */
@Data
public class QuerySysNotifyGroupBo extends BaseBo<SysNotifyGroup> {

  /**
   * ID
   */
  @Schema(description = "ID")
  private String id;

  /**
   * 名称
   */
  @Schema(description = "名称")
  private String name;

  /**
   * 接收者类型
   */
  @Schema(description = "接收者类型")
  private String receiverType;

  /**
   * 消息类型
   */
  @Schema(description = "消息类型")
  private String messageType;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

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

  public QuerySysNotifyGroupBo() {

  }

  public QuerySysNotifyGroupBo(SysNotifyGroup dto) {

    super(dto);
  }

  @Override
  @SuppressWarnings("unchecked")
  public BaseBo<SysNotifyGroup> convert(SysNotifyGroup dto) {
    return super.convert(dto, QuerySysNotifyGroupBo::getReceiverType,
        QuerySysNotifyGroupBo::getMessageType);
  }

  @Override
  protected void afterInit(SysNotifyGroup dto) {

    this.receiverType = dto.getReceiverType().getDesc();
    this.messageType = Arrays.stream(dto.getMessageType().split(StringPool.STR_SPLIT))
        .map(Integer::valueOf).map(
            t -> EnumUtil.getDesc(SysNotifyMessageType.class, t))
        .collect(Collectors.joining(StringPool.STR_SPLIT_CN));
  }
}
