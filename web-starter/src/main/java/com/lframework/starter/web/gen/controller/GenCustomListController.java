package com.lframework.starter.web.gen.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.gen.entity.GenCustomList;
import com.lframework.starter.web.gen.service.GenCustomListService;
import com.lframework.starter.web.gen.vo.custom.list.CreateGenCustomListVo;
import com.lframework.starter.web.gen.vo.custom.list.QueryGenCustomListVo;
import com.lframework.starter.web.gen.vo.custom.list.UpdateGenCustomListVo;
import com.lframework.starter.web.gen.bo.custom.list.GetGenCustomListBo;
import com.lframework.starter.web.gen.bo.custom.list.QueryGenCustomListBo;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "自定义列表")
@Slf4j
@Validated
@RestController
@RequestMapping("/gen/custom/list")
public class GenCustomListController extends DefaultBaseController {

  @Autowired
  private GenCustomListService genCustomListService;

  @ApiOperation("查询自定义列表")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryGenCustomListBo>> query(@Valid QueryGenCustomListVo vo) {

    PageResult<GenCustomList> pageResult = genCustomListService.query(getPageIndex(vo),
        getPageSize(vo),
        vo);
    List<GenCustomList> datas = pageResult.getDatas();
    List<QueryGenCustomListBo> results = null;
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(QueryGenCustomListBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetGenCustomListBo> get(@NotBlank(message = "ID不能为空！") String id) {

    GenCustomList data = genCustomListService.findById(id);

    return InvokeResultBuilder.success(new GetGenCustomListBo(data));
  }

  @ApiOperation("新增")
  @PostMapping
  public InvokeResult<Void> create(@RequestBody @Valid CreateGenCustomListVo vo) {

    genCustomListService.create(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("修改")
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateGenCustomListVo vo) {

    genCustomListService.update(vo);

    genCustomListService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("根据ID删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    genCustomListService.delete(id);

    genCustomListService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("启用")
  @PatchMapping("/enable")
  public InvokeResult<Void> enable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "ID不能为空！") String id) {

    genCustomListService.enable(id);

    genCustomListService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("停用")
  @PatchMapping("/unable")
  public InvokeResult<Void> unable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "ID不能为空！") String id) {

    genCustomListService.unable(id);

    genCustomListService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }
}
