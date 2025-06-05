package com.lframework.starter.bpm.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.bpm.bo.flow.instance.QueryBusinessFlowInstanceBo;
import com.lframework.starter.bpm.entity.FlowInstanceWrapper;
import com.lframework.starter.bpm.service.FlowInstanceWrapperService;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "流程实例")
@Validated
@RestController
@RequestMapping("/flow/instance")
public class FlowInstanceController extends DefaultBaseController {

  @Autowired
  private FlowInstanceWrapperService flowInstanceWrapperService;

  /**
   * 根据业务ID查询
   *
   * @return
   */
  @ApiOperation("根据业务ID查询")
  @ApiImplicitParam(value = "业务ID", name = "businessId", paramType = "query", required = true)
  @GetMapping("/list/business")
  public InvokeResult<List<QueryBusinessFlowInstanceBo>> listBusiness(
      @NotNull(message = "业务ID不能为空！") String businessId) {

    Wrapper<FlowInstanceWrapper> queryWrapper = Wrappers.lambdaQuery(FlowInstanceWrapper.class)
        .eq(FlowInstanceWrapper::getBusinessId, businessId)
        .orderByDesc(FlowInstanceWrapper::getCreateTime);

    List<FlowInstanceWrapper> datas = flowInstanceWrapperService.list(queryWrapper);

    List<QueryBusinessFlowInstanceBo> results = datas.stream().map(QueryBusinessFlowInstanceBo::new)
        .collect(
            Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("根据ID查询状态")
  @ApiImplicitParam(value = "流程实例ID", name = "id", paramType = "query", required = true)
  @GetMapping("/status")
  public InvokeResult<String> getStatus(@NotNull(message = "流程实例ID不能为空！") Long id) {
    FlowInstanceWrapper instance = flowInstanceWrapperService.getById(id);
    if(instance == null) {
      throw new DefaultClientException("流程实例不存在！");
    }
    return InvokeResultBuilder.success(instance.getFlowStatus().getCode());
  }
}
