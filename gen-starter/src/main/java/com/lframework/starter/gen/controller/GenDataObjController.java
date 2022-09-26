package com.lframework.starter.gen.controller;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.gen.bo.data.obj.GetGenDataObjBo;
import com.lframework.starter.gen.bo.data.obj.QueryGenDataObjBo;
import com.lframework.starter.gen.entity.GenDataObj;
import com.lframework.starter.gen.service.IGenDataObjService;
import com.lframework.starter.gen.vo.data.obj.CreateGenDataObjVo;
import com.lframework.starter.gen.vo.data.obj.QueryGenDataObjVo;
import com.lframework.starter.gen.vo.data.obj.UpdateGenDataObjVo;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
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

@Api(tags = "数据实体")
@Slf4j
@Validated
@RestController
@RequestMapping("/gen/data/obj")
public class GenDataObjController extends DefaultBaseController {

  @Autowired
  private IGenDataObjService genDataObjService;

  @ApiOperation("查询数据对象列表")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryGenDataObjBo>> query(@Valid QueryGenDataObjVo vo) {

    PageResult<GenDataObj> pageResult = genDataObjService.query(getPageIndex(vo), getPageSize(vo),
        vo);
    List<GenDataObj> datas = pageResult.getDatas();
    List<QueryGenDataObjBo> results = null;
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(QueryGenDataObjBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetGenDataObjBo> get(@NotBlank(message = "ID不能为空！") String id) {

    GenDataObj data = genDataObjService.findById(id);

    return InvokeResultBuilder.success(new GetGenDataObjBo(data));
  }

  @ApiOperation("新增")
  @PostMapping
  public InvokeResult<Void> create(@RequestBody @Valid CreateGenDataObjVo vo) {

    genDataObjService.create(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("修改")
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateGenDataObjVo vo) {

    genDataObjService.update(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("根据ID删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    genDataObjService.delete(id);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("批量删除")
  @DeleteMapping("/batch")
  public InvokeResult<Void> batchDelete(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "ID不能为空！") List<String> ids) {

    genDataObjService.batchDelete(ids);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("批量启用")
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "ID不能为空！") List<String> ids) {

    genDataObjService.batchEnable(ids);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("批量停用")
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "ID", required = true) @RequestBody @NotEmpty(message = "ID不能为空！") List<String> ids) {

    genDataObjService.batchUnable(ids);

    return InvokeResultBuilder.success();
  }
}
