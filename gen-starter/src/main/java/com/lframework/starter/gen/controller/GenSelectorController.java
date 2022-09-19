package com.lframework.starter.gen.controller;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.gen.bo.data.entity.category.GenDataEntityCategorySelectorBo;
import com.lframework.starter.gen.bo.simpledb.SimpleDBSelectorBo;
import com.lframework.starter.gen.dto.simpledb.SimpleDBDto;
import com.lframework.starter.gen.entity.GenDataEntityCategory;
import com.lframework.starter.gen.service.IGenDataEntityCategoryService;
import com.lframework.starter.gen.service.ISimpleDBService;
import com.lframework.starter.gen.vo.data.entity.category.GenDataEntityCategorySelectorVo;
import com.lframework.starter.gen.vo.simpledb.SimpleTableSelectorVo;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "代码生成-选择器")
@Slf4j
@Validated
@RestController
@RequestMapping("/selector/gen")
public class GenSelectorController extends DefaultBaseController {

  @Autowired
  private IGenDataEntityCategoryService genDataEntityCategoryService;

  @Autowired
  private ISimpleDBService simpleDBService;

  @ApiOperation("数据实体分类")
  @GetMapping("/data/entity/category")
  public InvokeResult<PageResult<GenDataEntityCategorySelectorBo>> position(
      @Valid GenDataEntityCategorySelectorVo vo) {

    PageResult<GenDataEntityCategory> pageResult = genDataEntityCategoryService.selector(
        getPageIndex(vo), getPageSize(vo), vo);
    List<GenDataEntityCategory> datas = pageResult.getDatas();
    List<GenDataEntityCategorySelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(GenDataEntityCategorySelectorBo::new)
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("数据表")
  @GetMapping("/table")
  public InvokeResult<PageResult<SimpleDBSelectorBo>> table(@Valid SimpleTableSelectorVo vo) {
    PageResult<SimpleDBDto> pageResult = simpleDBService.selector(getPageIndex(vo), getPageSize(vo),
        vo);
    List<SimpleDBDto> datas = pageResult.getDatas();
    List<SimpleDBSelectorBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(SimpleDBSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }
}
