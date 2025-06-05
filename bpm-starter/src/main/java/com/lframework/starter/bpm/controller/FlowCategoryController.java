package com.lframework.starter.bpm.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lframework.starter.bpm.bo.flow.category.DetailFlowCategoryBo;
import com.lframework.starter.bpm.bo.flow.category.QueryFlowCategoryTreeBo;
import com.lframework.starter.bpm.entity.FlowCategory;
import com.lframework.starter.bpm.service.FlowCategoryService;
import com.lframework.starter.bpm.vo.flow.category.CreateFlowCategoryVo;
import com.lframework.starter.bpm.vo.flow.category.UpdateFlowCategoryVo;
import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "流程分类")
@Validated
@RestController
@RequestMapping("/flow/category")
public class FlowCategoryController extends DefaultBaseController {

  @Autowired
  private FlowCategoryService flowCategoryService;

  /**
   * 根据ID查询
   *
   * @param id
   * @return
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping("/detail")
  public InvokeResult<DetailFlowCategoryBo> detail(
      @NotBlank(message = "ID不能为空！") String id) {
    FlowCategory data = flowCategoryService.getById(id);
    if (data == null) {
      throw new DefaultClientException("分类不存在！");
    }

    return InvokeResultBuilder.success(new DetailFlowCategoryBo(data));
  }

  /**
   * 查询列表
   *
   * @return
   */
  @ApiOperation("查询列表")
  @GetMapping("/query")
  public InvokeResult<List<QueryFlowCategoryTreeBo>> query() {
    List<FlowCategory> list = flowCategoryService.list();
    List<QueryFlowCategoryTreeBo> results = list.stream().map(QueryFlowCategoryTreeBo::new).collect(
        Collectors.toList());
    return InvokeResultBuilder.success(results);
  }

  /**
   * 新增
   *
   * @param vo
   * @return
   */
  @ApiOperation("新增")
  @PostMapping
  public InvokeResult<String> create(@RequestBody CreateFlowCategoryVo vo) {
    return InvokeResultBuilder.success(flowCategoryService.create(vo));
  }

  /**
   * 修改
   *
   * @param vo
   * @return
   */
  @ApiOperation("修改")
  @PutMapping
  public InvokeResult<Void> update(@RequestBody UpdateFlowCategoryVo vo) {
    flowCategoryService.update(vo);

    flowCategoryService.cleanCacheByKey(vo.getId());

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
  public InvokeResult<Void> deleteById(@NotBlank(message = "ID不能为空！") String id) {
    // 先查询有没有子节点，如果有子节点那么不允许删除
    Wrapper<FlowCategory> checkWrapper = Wrappers.lambdaQuery(FlowCategory.class)
        .eq(FlowCategory::getParentId, id);
    if (flowCategoryService.count(checkWrapper) > 0) {
      throw new DefaultClientException("当前分类存在子分类，不允许删除！");
    }
    flowCategoryService.removeById(id);

    flowCategoryService.cleanCacheByKey(id);
    return InvokeResultBuilder.success();
  }
}
