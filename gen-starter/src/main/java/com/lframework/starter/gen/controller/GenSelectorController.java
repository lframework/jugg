package com.lframework.starter.gen.controller;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.gen.bo.custom.list.category.GenCustomListCategorySelectorBo;
import com.lframework.starter.gen.bo.data.entity.GenDataEntityDetailSelectorBo;
import com.lframework.starter.gen.bo.data.entity.GenDataEntitySelectorBo;
import com.lframework.starter.gen.bo.data.entity.category.GenDataEntityCategorySelectorBo;
import com.lframework.starter.gen.bo.data.obj.GenDataObjSelectorBo;
import com.lframework.starter.gen.bo.data.obj.category.GenDataObjCategorySelectorBo;
import com.lframework.starter.gen.bo.simpledb.SimpleDBSelectorBo;
import com.lframework.starter.gen.dto.simpledb.SimpleDBDto;
import com.lframework.starter.gen.entity.GenCustomListCategory;
import com.lframework.starter.gen.entity.GenDataEntity;
import com.lframework.starter.gen.entity.GenDataEntityCategory;
import com.lframework.starter.gen.entity.GenDataEntityDetail;
import com.lframework.starter.gen.entity.GenDataObj;
import com.lframework.starter.gen.entity.GenDataObjCategory;
import com.lframework.starter.gen.service.IGenCustomListCategoryService;
import com.lframework.starter.gen.service.IGenDataEntityCategoryService;
import com.lframework.starter.gen.service.IGenDataEntityDetailService;
import com.lframework.starter.gen.service.IGenDataEntityService;
import com.lframework.starter.gen.service.IGenDataObjCategoryService;
import com.lframework.starter.gen.service.IGenDataObjService;
import com.lframework.starter.gen.service.ISimpleDBService;
import com.lframework.starter.gen.vo.custom.list.category.GenCustomListCategorySelectorVo;
import com.lframework.starter.gen.vo.data.entity.GenDataEntityDetailSelectorVo;
import com.lframework.starter.gen.vo.data.entity.GenDataEntitySelectorVo;
import com.lframework.starter.gen.vo.data.entity.category.GenDataEntityCategorySelectorVo;
import com.lframework.starter.gen.vo.data.obj.GenDataObjSelectorVo;
import com.lframework.starter.gen.vo.data.obj.category.GenDataObjCategorySelectorVo;
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
  private IGenDataObjCategoryService genDataObjCategoryService;

  @Autowired
  private ISimpleDBService simpleDBService;

  @Autowired
  private IGenDataEntityService genDataEntityService;

  @Autowired
  private IGenDataEntityDetailService genDataEntityDetailService;

  @Autowired
  private IGenDataObjService genDataObjService;

  @Autowired
  private IGenCustomListCategoryService genCustomListCategoryService;

  @ApiOperation("数据实体分类")
  @GetMapping("/data/entity/category")
  public InvokeResult<PageResult<GenDataEntityCategorySelectorBo>> dataEntityCategory(
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

  @ApiOperation("数据对象分类")
  @GetMapping("/data/obj/category")
  public InvokeResult<PageResult<GenDataObjCategorySelectorBo>> dataObjCategory(
      @Valid GenDataObjCategorySelectorVo vo) {

    PageResult<GenDataObjCategory> pageResult = genDataObjCategoryService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<GenDataObjCategory> datas = pageResult.getDatas();
    List<GenDataObjCategorySelectorBo> results = null;

    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(GenDataObjCategorySelectorBo::new).collect(Collectors.toList());
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

  @ApiOperation("数据实体")
  @GetMapping("/data/entity")
  public InvokeResult<PageResult<GenDataEntitySelectorBo>> dataEntity(
      @Valid GenDataEntitySelectorVo vo) {
    PageResult<GenDataEntity> pageResult = genDataEntityService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<GenDataEntity> datas = pageResult.getDatas();
    List<GenDataEntitySelectorBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(GenDataEntitySelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("数据实体明细")
  @GetMapping("/data/entity/detail")
  public InvokeResult<List<GenDataEntityDetailSelectorBo>> dataEntityDetail(
      @Valid GenDataEntityDetailSelectorVo vo) {
    List<GenDataEntityDetail> datas = genDataEntityDetailService.selector(vo);
    List<GenDataEntityDetailSelectorBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(GenDataEntityDetailSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("数据对象")
  @GetMapping("/data/obj")
  public InvokeResult<PageResult<GenDataObjSelectorBo>> dataEntity(@Valid GenDataObjSelectorVo vo) {
    PageResult<GenDataObj> pageResult = genDataObjService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<GenDataObj> datas = pageResult.getDatas();
    List<GenDataObjSelectorBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(GenDataObjSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  @ApiOperation("自定义列表分类")
  @GetMapping("/custom/list/category")
  public InvokeResult<PageResult<GenCustomListCategorySelectorBo>> dataEntity(
      @Valid GenCustomListCategorySelectorVo vo) {
    PageResult<GenCustomListCategory> pageResult = genCustomListCategoryService.selector(
        getPageIndex(vo),
        getPageSize(vo), vo);
    List<GenCustomListCategory> datas = pageResult.getDatas();
    List<GenCustomListCategorySelectorBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(GenCustomListCategorySelectorBo::new)
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }
}
