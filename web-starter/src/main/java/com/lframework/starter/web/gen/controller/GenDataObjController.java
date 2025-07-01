package com.lframework.starter.web.gen.controller;

import com.lframework.starter.common.exceptions.impl.DefaultClientException;
import com.lframework.starter.common.utils.CollectionUtil;
import com.lframework.starter.web.gen.bo.data.obj.GenDataObjColumnBo;
import com.lframework.starter.web.gen.bo.data.obj.GenDataObjColumnBo.ColumnBo;
import com.lframework.starter.web.gen.bo.data.obj.GetGenDataObjBo;
import com.lframework.starter.web.gen.bo.data.obj.QueryGenDataObjBo;
import com.lframework.starter.web.gen.entity.GenDataEntity;
import com.lframework.starter.web.gen.entity.GenDataEntityDetail;
import com.lframework.starter.web.gen.entity.GenDataObjDetail;
import com.lframework.starter.web.gen.entity.GenDataObjQueryDetail;
import com.lframework.starter.web.gen.service.GenDataEntityDetailService;
import com.lframework.starter.web.gen.service.GenDataEntityService;
import com.lframework.starter.web.gen.service.GenDataObjDetailService;
import com.lframework.starter.web.gen.service.GenDataObjQueryDetailService;
import com.lframework.starter.web.gen.service.GenDataObjService;
import com.lframework.starter.web.gen.vo.data.obj.CreateGenDataObjVo;
import com.lframework.starter.web.gen.vo.data.obj.QueryGenDataObjVo;
import com.lframework.starter.web.gen.vo.data.obj.UpdateGenDataObjVo;
import com.lframework.starter.web.gen.entity.GenDataObj;
import com.lframework.starter.web.gen.enums.GenCustomListDetailType;
import com.lframework.starter.web.core.components.resp.PageResult;
import com.lframework.starter.web.core.utils.PageResultUtil;
import com.lframework.starter.web.core.controller.DefaultBaseController;
import com.lframework.starter.web.core.components.resp.InvokeResult;
import com.lframework.starter.web.core.components.resp.InvokeResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.ArrayList;
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

@Api(tags = "数据对象")
@Slf4j
@Validated
@RestController
@RequestMapping("/gen/data/obj")
public class GenDataObjController extends DefaultBaseController {

  @Autowired
  private GenDataObjService genDataObjService;

  @Autowired
  private GenDataObjDetailService genDataObjDetailService;

  @Autowired
  private GenDataObjQueryDetailService genDataObjQueryDetailService;

  @Autowired
  private GenDataEntityService genDataEntityService;

  @Autowired
  private GenDataEntityDetailService genDataEntityDetailService;

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

  @ApiOperation("查询数据对象列")
  @ApiImplicitParam(value = "数据对象ID", name = "id", paramType = "query", required = true)
  @GetMapping("/columns")
  public InvokeResult<List<GenDataObjColumnBo>> queryColumns(
      @NotBlank(message = "ID不能为空！") String id) {

    GenDataObj dataObj = genDataObjService.findById(id);
    if (dataObj == null) {
      throw new DefaultClientException("数据对象不存在！");
    }

    List<GenDataObjDetail> dataObjDetails = genDataObjDetailService.getByObjId(dataObj.getId());

    List<GenDataObjQueryDetail> dataObjQueryDetails = genDataObjQueryDetailService.getByObjId(
        dataObj.getId());

    List<GenDataObjColumnBo> results = new ArrayList<>();
    // 先查主表
    GenDataEntity dataEntity = genDataEntityService.findById(dataObj.getMainTableId());
    if (dataEntity == null) {
      throw new DefaultClientException("主表已被删除，请检查！");
    }

    List<GenDataEntityDetail> dataEntityDetails = genDataEntityDetailService.getByEntityId(
        dataEntity.getId());

    GenDataObjColumnBo mainTable = new GenDataObjColumnBo();
    mainTable.setColumns(new ArrayList<>());
    mainTable.setId(dataEntity.getId());
    mainTable.setName("【主表】" + dataEntity.getName());
    for (GenDataEntityDetail dataEntityDetail : dataEntityDetails) {
      ColumnBo column = new ColumnBo();
      column.setId(dataEntityDetail.getId());
      column.setRelaId(dataObj.getId());
      column.setName(dataEntityDetail.getName());
      column.setType(GenCustomListDetailType.MAIN_TABLE.getCode());
      column.setDataType(dataEntityDetail.getDataType().getCode());
      column.setViewType(dataEntityDetail.getViewType().getCode());
      mainTable.getColumns().add(column);
    }

    results.add(mainTable);

    // 再查子表
    if (!CollectionUtil.isEmpty(dataObjDetails)) {
      for (GenDataObjDetail dataObjDetail : dataObjDetails) {
        GenDataEntity subDataEntity = genDataEntityService.findById(dataObjDetail.getSubTableId());
        if (subDataEntity == null) {
          throw new DefaultClientException("子表已被删除，请检查！");
        }

        List<GenDataEntityDetail> subDataEntityDetails = genDataEntityDetailService.getByEntityId(
            subDataEntity.getId());

        GenDataObjColumnBo subTable = new GenDataObjColumnBo();
        subTable.setColumns(new ArrayList<>());
        subTable.setId(subDataEntity.getId());
        subTable.setName("【子表】" + subDataEntity.getName());
        for (GenDataEntityDetail dataEntityDetail : subDataEntityDetails) {
          ColumnBo column = new ColumnBo();
          column.setId(dataEntityDetail.getId());
          column.setRelaId(dataObjDetail.getId());
          column.setName(dataEntityDetail.getName());
          column.setType(GenCustomListDetailType.SUB_TALBE.getCode());
          column.setDataType(dataEntityDetail.getDataType().getCode());
          column.setViewType(dataEntityDetail.getViewType().getCode());
          subTable.getColumns().add(column);
        }

        results.add(subTable);
      }
    }

    // 最后查附加字段
    if (!CollectionUtil.isEmpty(dataObjQueryDetails)) {
      GenDataObjColumnBo customQuery = new GenDataObjColumnBo();
      customQuery.setColumns(new ArrayList<>());
      customQuery.setId("customQuery");
      customQuery.setName("自定义查询");
      for (GenDataObjQueryDetail genDataObjDetail : dataObjQueryDetails) {
        ColumnBo column = new ColumnBo();
        column.setId(genDataObjDetail.getId());
        column.setRelaId(genDataObjDetail.getId());
        column.setName(genDataObjDetail.getCustomName());
        column.setType(GenCustomListDetailType.CUSTOM.getCode());
        column.setDataType(genDataObjDetail.getDataType().getCode());
        customQuery.getColumns().add(column);
      }

      results.add(customQuery);
    }

    return InvokeResultBuilder.success(results);
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

    genDataObjService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  @ApiOperation("根据ID删除")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    genDataObjService.delete(id);

    genDataObjService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("启用")
  @PatchMapping("/enable")
  public InvokeResult<Void> enable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "ID不能为空！") String id) {

    genDataObjService.enable(id);

    genDataObjService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  @ApiOperation("停用")
  @PatchMapping("/unable")
  public InvokeResult<Void> unable(
      @ApiParam(value = "ID", required = true) @NotEmpty(message = "ID不能为空！") String id) {

    genDataObjService.unable(id);

    genDataObjService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }
}
