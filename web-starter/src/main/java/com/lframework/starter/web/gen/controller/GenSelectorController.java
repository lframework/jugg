package com.lframework.starter.web.gen.controller;

import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.common.utils.StringUtil;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.gen.bo.custom.list.GenCustomListSelectorBo;
import com.lframework.starter.web.gen.bo.custom.list.category.GenCustomListCategorySelectorBo;
import com.lframework.starter.web.gen.bo.custom.page.GenCustomPageSelectorBo;
import com.lframework.starter.web.gen.bo.custom.page.category.GenCustomPageCategorySelectorBo;
import com.lframework.starter.web.gen.bo.custom.selector.GenCustomSelectorSelectorBo;
import com.lframework.starter.web.gen.bo.custom.selector.category.GenCustomSelectorCategorySelectorBo;
import com.lframework.starter.web.gen.bo.data.entity.GenDataEntityDetailSelectorBo;
import com.lframework.starter.web.gen.bo.data.entity.GenDataEntitySelectorBo;
import com.lframework.starter.web.gen.bo.data.entity.category.GenDataEntityCategorySelectorBo;
import com.lframework.starter.web.gen.bo.data.obj.GenDataObjSelectorBo;
import com.lframework.starter.web.gen.bo.data.obj.category.GenDataObjCategorySelectorBo;
import com.lframework.starter.web.gen.bo.simpledb.SimpleDBSelectorBo;
import com.lframework.starter.web.gen.dto.simpledb.SimpleDBDto;
import com.lframework.starter.web.gen.entity.GenCustomList;
import com.lframework.starter.web.gen.entity.GenCustomListCategory;
import com.lframework.starter.web.gen.entity.GenCustomPage;
import com.lframework.starter.web.gen.entity.GenCustomPageCategory;
import com.lframework.starter.web.gen.entity.GenCustomSelector;
import com.lframework.starter.web.gen.entity.GenCustomSelectorCategory;
import com.lframework.starter.web.gen.entity.GenDataEntity;
import com.lframework.starter.web.gen.entity.GenDataEntityCategory;
import com.lframework.starter.web.gen.entity.GenDataEntityDetail;
import com.lframework.starter.web.gen.entity.GenDataObj;
import com.lframework.starter.web.gen.entity.GenDataObjCategory;
import com.lframework.starter.web.gen.service.GenCustomListCategoryService;
import com.lframework.starter.web.gen.service.GenCustomListService;
import com.lframework.starter.web.gen.service.GenCustomPageCategoryService;
import com.lframework.starter.web.gen.service.GenCustomPageService;
import com.lframework.starter.web.gen.service.GenCustomSelectorCategoryService;
import com.lframework.starter.web.gen.service.GenCustomSelectorService;
import com.lframework.starter.web.gen.service.GenDataEntityCategoryService;
import com.lframework.starter.web.gen.service.GenDataEntityDetailService;
import com.lframework.starter.web.gen.service.GenDataEntityService;
import com.lframework.starter.web.gen.service.GenDataObjCategoryService;
import com.lframework.starter.web.gen.service.GenDataObjService;
import com.lframework.starter.web.gen.service.SimpleDBService;
import com.lframework.starter.web.gen.vo.custom.list.GenCustomListSelectorVo;
import com.lframework.starter.web.gen.vo.custom.list.category.GenCustomListCategorySelectorVo;
import com.lframework.starter.web.gen.vo.custom.page.GenCustomPageSelectorVo;
import com.lframework.starter.web.gen.vo.custom.page.category.GenCustomPageCategorySelectorVo;
import com.lframework.starter.web.gen.vo.custom.selector.GenCustomSelectorSelectorVo;
import com.lframework.starter.web.gen.vo.custom.selector.category.GenCustomSelectorCategorySelectorVo;
import com.lframework.starter.web.gen.vo.data.entity.GenDataEntityDetailSelectorVo;
import com.lframework.starter.web.gen.vo.data.entity.GenDataEntitySelectorVo;
import com.lframework.starter.web.gen.vo.data.entity.category.GenDataEntityCategorySelectorVo;
import com.lframework.starter.web.gen.vo.data.obj.GenDataObjSelectorVo;
import com.lframework.starter.web.gen.vo.data.obj.category.GenDataObjCategorySelectorVo;
import com.lframework.starter.web.gen.vo.simpledb.SimpleTableSelectorVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "代码生成-选择器")
@Slf4j
@Validated
@RestController
@RequestMapping("/selector/gen")
public class GenSelectorController extends DefaultBaseController {

  @Autowired
  private GenDataEntityCategoryService genDataEntityCategoryService;

  @Autowired
  private GenDataObjCategoryService genDataObjCategoryService;

  @Autowired
  private SimpleDBService simpleDBService;

  @Autowired
  private GenDataEntityService genDataEntityService;

  @Autowired
  private GenDataEntityDetailService genDataEntityDetailService;

  @Autowired
  private GenDataObjService genDataObjService;

  @Autowired
  private GenCustomListCategoryService genCustomListCategoryService;

  @Autowired
  private GenCustomListService genCustomListService;

  @Autowired
  private GenCustomSelectorCategoryService genCustomSelectorCategoryService;

  @Autowired
  private GenCustomSelectorService genCustomSelectorService;

  @Autowired
  private GenCustomPageCategoryService genCustomPageCategoryService;

  @Autowired
  private GenCustomPageService genCustomPageService;

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

  /**
   * 加载数据实体分类
   */
  @ApiOperation("加载数据实体分类")
  @PostMapping("/data/entity/category/load")
  public InvokeResult<List<GenDataEntityCategorySelectorBo>> loadDataEntityCategory(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<GenDataEntityCategory> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> genDataEntityCategoryService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<GenDataEntityCategorySelectorBo> results = datas.stream()
        .map(GenDataEntityCategorySelectorBo::new).collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
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

  /**
   * 加载数据对象分类
   */
  @ApiOperation("加载数据对象分类")
  @PostMapping("/data/obj/category/load")
  public InvokeResult<List<GenDataObjCategorySelectorBo>> loadDataObjCategory(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<GenDataObjCategory> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> genDataObjCategoryService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<GenDataObjCategorySelectorBo> results = datas.stream()
        .map(GenDataObjCategorySelectorBo::new).collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
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

  /**
   * 加载数据表
   */
  @ApiOperation("加载数据表")
  @PostMapping("/table/load")
  public InvokeResult<List<SimpleDBSelectorBo>> loadTable(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<SimpleDBDto> datas = simpleDBService.listByIds(ids);
    List<SimpleDBSelectorBo> results = datas.stream().map(SimpleDBSelectorBo::new)
        .collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
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

  /**
   * 加载数据实体
   */
  @ApiOperation("加载数据实体")
  @PostMapping("/data/entity/load")
  public InvokeResult<List<GenDataEntitySelectorBo>> loadDataEntity(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<GenDataEntity> datas = genDataEntityService.listByIds(ids);
    List<GenDataEntitySelectorBo> results = datas.stream().map(GenDataEntitySelectorBo::new)
        .collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
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
  public InvokeResult<PageResult<GenDataObjSelectorBo>> dataObj(@Valid GenDataObjSelectorVo vo) {
    PageResult<GenDataObj> pageResult = genDataObjService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<GenDataObj> datas = pageResult.getDatas();
    List<GenDataObjSelectorBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(GenDataObjSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载数据对象
   */
  @ApiOperation("加载数据对象")
  @PostMapping("/data/obj/load")
  public InvokeResult<List<GenDataObjSelectorBo>> loadDataObj(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<GenDataObj> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> genDataObjService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<GenDataObjSelectorBo> results = datas.stream().map(GenDataObjSelectorBo::new).collect(
        Collectors.toList());
    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("自定义列表分类")
  @GetMapping("/custom/list/category")
  public InvokeResult<PageResult<GenCustomListCategorySelectorBo>> customListCategory(
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

  /**
   * 加载自定义列表分类
   */
  @ApiOperation("加载自定义列表分类")
  @PostMapping("/custom/list/category/load")
  public InvokeResult<List<GenCustomListCategorySelectorBo>> loadCustomListCategory(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<GenCustomListCategory> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> genCustomListCategoryService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<GenCustomListCategorySelectorBo> results = datas.stream()
        .map(GenCustomListCategorySelectorBo::new).collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("自定义列表")
  @GetMapping("/custom/list")
  public InvokeResult<PageResult<GenCustomListSelectorBo>> customList(
      @Valid GenCustomListSelectorVo vo) {
    PageResult<GenCustomList> pageResult = genCustomListService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<GenCustomList> datas = pageResult.getDatas();
    List<GenCustomListSelectorBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(GenCustomListSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载自定义列表
   */
  @ApiOperation("加载自定义列表")
  @PostMapping("/custom/list/load")
  public InvokeResult<List<GenCustomListSelectorBo>> loadCustomList(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<GenCustomList> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> genCustomListService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<GenCustomListSelectorBo> results = datas.stream()
        .map(GenCustomListSelectorBo::new).collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("自定义选择器分类")
  @GetMapping("/custom/selector/category")
  public InvokeResult<PageResult<GenCustomSelectorCategorySelectorBo>> customSelectorCategory(
      @Valid GenCustomSelectorCategorySelectorVo vo) {
    PageResult<GenCustomSelectorCategory> pageResult = genCustomSelectorCategoryService.selector(
        getPageIndex(vo),
        getPageSize(vo), vo);
    List<GenCustomSelectorCategory> datas = pageResult.getDatas();
    List<GenCustomSelectorCategorySelectorBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(GenCustomSelectorCategorySelectorBo::new)
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载自定义选择器分类
   */
  @ApiOperation("加载自定义选择器分类")
  @PostMapping("/custom/selector/category/load")
  public InvokeResult<List<GenCustomSelectorCategorySelectorBo>> loadCustomSelectorCategory(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<GenCustomSelectorCategory> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> genCustomSelectorCategoryService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<GenCustomSelectorCategorySelectorBo> results = datas.stream()
        .map(GenCustomSelectorCategorySelectorBo::new).collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("自定义选择器")
  @GetMapping("/custom/selector")
  public InvokeResult<PageResult<GenCustomSelectorSelectorBo>> customList(
      @Valid GenCustomSelectorSelectorVo vo) {
    PageResult<GenCustomSelector> pageResult = genCustomSelectorService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<GenCustomSelector> datas = pageResult.getDatas();
    List<GenCustomSelectorSelectorBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(GenCustomSelectorSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载自定义选择器
   */
  @ApiOperation("加载自定义选择器")
  @PostMapping("/custom/selector/load")
  public InvokeResult<List<GenCustomSelectorSelectorBo>> loadCustomSelector(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<GenCustomSelector> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> genCustomSelectorService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<GenCustomSelectorSelectorBo> results = datas.stream()
        .map(GenCustomSelectorSelectorBo::new).collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("自定义页面分类")
  @GetMapping("/custom/page/category")
  public InvokeResult<PageResult<GenCustomPageCategorySelectorBo>> customPageCategory(@Valid GenCustomPageCategorySelectorVo vo) {

    PageResult<GenCustomPageCategory> pageResult = genCustomPageCategoryService.selector(
        getPageIndex(vo),
        getPageSize(vo), vo);
    List<GenCustomPageCategory> datas = pageResult.getDatas();
    List<GenCustomPageCategorySelectorBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(GenCustomPageCategorySelectorBo::new)
          .collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载自定义表单分类
   */
  @ApiOperation("加载自定义页面分类")
  @PostMapping("/custom/page/category/load")
  public InvokeResult<List<GenCustomPageCategorySelectorBo>> loadCustomPageCategory(
      @RequestBody(required = false) List<String> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<GenCustomPageCategory> datas = ids.stream().filter(StringUtil::isNotBlank)
        .map(t -> genCustomPageCategoryService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<GenCustomPageCategorySelectorBo> results = datas.stream()
        .map(GenCustomPageCategorySelectorBo::new).collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
  }

  @ApiOperation("自定义页面")
  @GetMapping("/custom/page")
  public InvokeResult<PageResult<GenCustomPageSelectorBo>> customPage(
      @Valid GenCustomPageSelectorVo vo) {
    PageResult<GenCustomPage> pageResult = genCustomPageService.selector(getPageIndex(vo),
        getPageSize(vo), vo);
    List<GenCustomPage> datas = pageResult.getDatas();
    List<GenCustomPageSelectorBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(GenCustomPageSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 加载自定义表单
   */
  @ApiOperation("加载自定义页面")
  @PostMapping("/custom/page/load")
  public InvokeResult<List<GenCustomPageSelectorBo>> loadCustomPage(
      @RequestBody(required = false) List<Integer> ids) {

    if (CollectionUtil.isEmpty(ids)) {
      return InvokeResultBuilder.success(CollectionUtil.emptyList());
    }

    List<GenCustomPage> datas = ids.stream().filter(Objects::nonNull)
        .map(t -> genCustomPageService.findById(t))
        .filter(Objects::nonNull).collect(Collectors.toList());
    List<GenCustomPageSelectorBo> results = datas.stream()
        .map(GenCustomPageSelectorBo::new).collect(
            Collectors.toList());
    return InvokeResultBuilder.success(results);
  }
}
