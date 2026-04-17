package com.lframework.starter.web.inner.bo.system.notify;

import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import com.lframework.starter.web.inner.entity.SysNotifyGroup;
import com.lframework.starter.web.inner.service.system.SysNotifyGroupReceiverService;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;

/**
 * <p>
 * 消息通知组 GetBo
 * </p>
 *
 * @author zmj
 */
@Data
public class GetSysNotifyGroupBo extends BaseBo<SysNotifyGroup> {

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
  private Integer receiverType;

  /**
   * 接收者ID
   */
  @Schema(description = "接收者ID")
  private List<String> receiverIds;

  /**
   * 消息类型
   */
  @Schema(description = "消息类型")
  private List<Integer> messageType;

  /**
   * 备注
   */
  @Schema(description = "备注")
  private String description;

  public GetSysNotifyGroupBo() {

  }

  public GetSysNotifyGroupBo(SysNotifyGroup dto) {

    super(dto);
  }

  @Override
  @SuppressWarnings("unchecked")
  public BaseBo<SysNotifyGroup> convert(SysNotifyGroup dto) {
    return super.convert(dto, GetSysNotifyGroupBo::getReceiverType,
        GetSysNotifyGroupBo::getMessageType);
  }

  @Override
  protected void afterInit(SysNotifyGroup dto) {

    this.receiverType = dto.getReceiverType().getCode();

    SysNotifyGroupReceiverService sysNotifyGroupReceiverService = ApplicationUtil.getBean(
        SysNotifyGroupReceiverService.class);
    this.receiverIds = sysNotifyGroupReceiverService.getReceiverIdsByGroupId(dto.getId());

    this.messageType = Arrays.stream(dto.getMessageType().split(StringPool.STR_SPLIT))
        .map(Integer::valueOf).collect(
            Collectors.toList());
  }
}
