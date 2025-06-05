package com.lframework.starter.bpm.bo.flow.instance;

import com.lframework.starter.bpm.entity.FlowInstanceWrapper;
import com.lframework.starter.bpm.service.FlowCuInstanceService;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class QueryBusinessFlowInstanceBo extends BaseBo<FlowInstanceWrapper> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 标题
   */
  @ApiModelProperty("标题")
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
