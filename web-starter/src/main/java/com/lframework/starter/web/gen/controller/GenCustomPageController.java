package com.lframework.starter.web.gen.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.gen.bo.custom.page.GetGenCustomPageBo;
import com.lframework.starter.web.gen.bo.custom.page.QueryGenCustomPageBo;
import com.lframework.starter.web.gen.entity.GenCustomPage;
import com.lframework.starter.web.gen.service.GenCustomPageService;
import com.lframework.starter.web.gen.vo.custom.page.CreateGenCustomPageVo;
import com.lframework.starter.web.gen.vo.custom.page.QueryGenCustomPageVo;
import com.lframework.starter.web.gen.vo.custom.page.UpdateGenCustomPageVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "自定义页面")
@Slf4j
@Validated
@RestController
@RequestMapping("/gen/custom/page")
public class GenCustomPageController extends DefaultBaseController {

  @Autowired
  private GenCustomPageService genCustomPageService;

  @ApiOperation("查询自定义页面")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryGenCustomPageBo>> query(@Valid QueryGenCustomPageVo vo) {

    PageResult<GenCustomPage> pageResult = genCustomPageService.query(getPageIndex(vo),
        getPageSize(vo), vo);
    List<GenCustomPage> datas = pageResult.getDatas();
    List<QueryGenCustomPageBo> results = null;
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(QueryGenCustomPageBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @GetMapping
  public InvokeResult<GetGenCustomPageBo> getConfig(@NotNull(message = "ID不能为空！") Integer id) {

    GenCustomPage data = genCustomPageService.findById(id);

    return InvokeResultBuilder.success(new GetGenCustomPageBo(data));
  }

  @ApiOperation("新增")
  @PostMapping
  public InvokeResult<Void> create(@RequestBody @Valid CreateGenCustomPageVo vo) {

    genCustomPageService.create(vo);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("修改")
  @PutMapping
  public InvokeResult<Void> update(@RequestBody @Valid UpdateGenCustomPageVo vo) {

    genCustomPageService.update(vo);

    genCustomPageService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("根据ID删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @DeleteMapping
  public InvokeResult<Void> delete(@NotNull(message = "ID不能为空！") Integer id) {

    genCustomPageService.delete(id);

    genCustomPageService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }
}
