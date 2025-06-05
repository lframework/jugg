package com.lframework.starter.bpm.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.github.pagehelper.PageInfo;
import com.lframework.starter.bpm.bo.flow.definition.DetailFlowDefinitionBo;
import com.lframework.starter.bpm.bo.flow.definition.QueryFlowDefinitionBo;
import com.lframework.starter.bpm.entity.FlowDefinitionWrapper;
import com.lframework.starter.bpm.enums.FlowDefinitionActivityStatus;
import com.lframework.starter.bpm.enums.FlowDefinitionIsPublish;
import com.lframework.starter.bpm.service.FlowDefinitionWrapperService;
import com.lframework.starter.bpm.vo.flow.definition.CreateFlowDefinitionVo;
import com.lframework.starter.bpm.vo.flow.definition.QueryFlowDefinitionVo;
import com.lframework.starter.bpm.vo.flow.definition.SetFlowDefinitionActivityStatusVo;
import com.lframework.starter.bpm.vo.flow.definition.SetFlowDefinitionPublishVo;
import com.lframework.starter.bpm.vo.flow.definition.UpdateFlowDefinitionVo;
import com.lframework.starter.common.constants.StringPool;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.utils.PageHelperUtil;
import com.lframework.starter.web.core.utils.PageResultUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.dromara.warm.flow.core.service.DefService;
import org.dromara.warm.flow.orm.entity.FlowDefinition;
import org.dromara.warm.flow.orm.mapper.FlowDefinitionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "流程定义")
@Validated
@RestController
@RequestMapping("/flow/definition")
public class FlowDefinitionController extends DefaultBaseController {

  @Autowired
  private DefService defService;

  @Autowired
  private FlowDefinitionWrapperService flowDefinitionWrapperService;

  /**
   * 查询列表
   *
   * @return
   */
  @ApiOperation("查询列表")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryFlowDefinitionBo>> query(@Valid QueryFlowDefinitionVo vo) {
    PageHelperUtil.startPage(vo);
    Wrapper<FlowDefinitionWrapper> queryWrapper = Wrappers.lambdaQuery(FlowDefinitionWrapper.class)
        .eq(!StringUtil.isBlank(vo.getCode()), FlowDefinitionWrapper::getFlowCode, vo.getCode())
        .like(!StringUtil.isBlank(vo.getName()), FlowDefinitionWrapper::getFlowName, vo.getName())
        .eq(!StringUtil.isBlank(vo.getVersion()), FlowDefinitionWrapper::getVersion,
            vo.getVersion())
        .eq(!StringUtil.isBlank(vo.getCategoryId()), FlowDefinitionWrapper::getCategory,
            vo.getCategoryId())
        .eq(vo.getIsPublish() != null, FlowDefinitionWrapper::getIsPublish, vo.getIsPublish())
        .eq(vo.getActivityStatus() != null, FlowDefinitionWrapper::getActivityStatus,
            vo.getActivityStatus())
        .orderByDesc(FlowDefinitionWrapper::getCreateTime);
    List<FlowDefinitionWrapper> datas = flowDefinitionWrapperService.list(queryWrapper);
    PageResult<FlowDefinitionWrapper> pageResult = PageResultUtil.convert(new PageInfo<>(datas));

    List<QueryFlowDefinitionBo> results = datas.stream().map(QueryFlowDefinitionBo::new)
        .collect(Collectors.toList());
    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("查询详情")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping("/detail")
  public InvokeResult<DetailFlowDefinitionBo> detail(@NotNull(message = "ID不能为空！") Long id) {
    FlowDefinitionWrapper data = flowDefinitionWrapperService.getById(id);
    if (data == null) {
      throw new DefaultClientException("流程不存在！");
    }

    return InvokeResultBuilder.success(new DetailFlowDefinitionBo(data));
  }

  /**
   * 新增
   *
   * @param vo
   * @return
   */
  @ApiOperation("新增")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateFlowDefinitionVo vo) {

    if (!CollectionUtil.isEmpty(defService.getByFlowCode(vo.getCode()))) {
      throw new DefaultClientException("流程编号重复，请重新输入！");
    }
    FlowDefinition definition = new FlowDefinition();
    definition.setFlowCode(vo.getCode());
    definition.setFlowName(vo.getName());
    definition.setCategory(vo.getCategoryId());
    definition.setVersion("1");
    definition.setIsPublish(FlowDefinitionIsPublish.N.getCode());
    definition.setFormCustom(StringPool.N);
    definition.setActivityStatus(FlowDefinitionActivityStatus.ACTIVATE.getCode());

    defService.saveAndInitNode(definition);
    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   *
   * @param vo
   * @return
   */
  @ApiOperation("修改")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateFlowDefinitionVo vo) {
    flowDefinitionWrapperService.update(vo);
    return InvokeResultBuilder.success();
  }

  /**
   * 设置发布状态
   *
   * @param vo
   * @return
   */
  @ApiOperation("设置发布状态")
  @PostMapping("/publish")
  public InvokeResult<Void> setPublishStatus(@Valid SetFlowDefinitionPublishVo vo) {
    if (vo.getIsPublish() == FlowDefinitionIsPublish.Y.getCode().intValue()) {
      defService.publish(vo.getId());
    } else {
      defService.unPublish(vo.getId());
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 设置激活状态
   *
   * @param vo
   * @return
   */
  @ApiOperation("设置激活状态")
  @PostMapping("/activity")
  public InvokeResult<Void> setActivityStatus(@Valid SetFlowDefinitionActivityStatusVo vo) {
    if (vo.getActivityStatus() == FlowDefinitionActivityStatus.ACTIVATE.getCode().intValue()) {
      defService.active(vo.getId());
    } else {
      defService.unActive(vo.getId());
    }

    return InvokeResultBuilder.success();
  }

  /**
   * 复制流程
   *
   * @param vo
   * @return
   */
  @ApiOperation("复制流程")
  @PostMapping("/copy")
  public InvokeResult<Void> copy(@Valid UpdateFlowDefinitionVo vo) {
    flowDefinitionWrapperService.copy(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 根据ID删除
   *
   * @param id
   * @return
   */
  @ApiOperation("根据ID删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @DeleteMapping
  public InvokeResult<Void> deleteById(@NotNull(message = "ID不能为空！") Long id) {
    defService.removeDef(Collections.singletonList(id));

    return InvokeResultBuilder.success();
  }
}
