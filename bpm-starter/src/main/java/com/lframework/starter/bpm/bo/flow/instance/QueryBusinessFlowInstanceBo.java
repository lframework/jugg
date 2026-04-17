package com.lframework.starter.bpm.bo.flow.instance;

import com.lframework.starter.bpm.entity.FlowInstanceWrapper;
import com.lframework.starter.bpm.service.FlowCuInstanceService;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class QueryBusinessFlowInstanceBo extends BaseBo<FlowInstanceWrapper> {

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

  public QueryBusinessFlowInstanceBo(FlowInstanceWrapper dto) {
    super(dto);
  }

  @Override
  protected void afterInit(FlowInstanceWrapper dto) {
    FlowCuInstanceService flowCuInstanceService = ApplicationUtil.getBean(
        FlowCuInstanceService.class);

    this.title = flowCuInstanceService.getById(dto.getId()).getTitle();
  }
}
