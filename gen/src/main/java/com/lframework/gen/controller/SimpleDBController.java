package com.lframework.gen.controller;

import com.lframework.common.utils.CollectionUtil;
import com.lframework.gen.bo.simpledb.GetSimpleTableBo;
import com.lframework.gen.bo.simpledb.SimpleDBSelectorBo;
import com.lframework.gen.dto.simpledb.SimpleDBDto;
import com.lframework.gen.dto.simpledb.SimpleTableDto;
import com.lframework.gen.service.ISimpleDBService;
import com.lframework.gen.service.ISimpleTableService;
import com.lframework.gen.vo.simpledb.CreateSimpleTableVo;
import com.lframework.gen.vo.simpledb.GetTablesVo;
import com.lframework.starter.web.controller.BaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/gen/simpledb")
public class SimpleDBController extends BaseController {

  @Autowired
  private ISimpleDBService simpleDBService;

  @Autowired
  private ISimpleTableService simpleTableService;

  @GetMapping("/tables")
  public InvokeResult getTables(@Valid GetTablesVo vo) {

    List<SimpleDBDto> datas = (vo.getIsCurrentDb() != null && vo.getIsCurrentDb()) ?
        simpleDBService.getCurrentTables() :
        simpleDBService.getTables(null);
    List<SimpleDBSelectorBo> results = Collections.EMPTY_LIST;
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(SimpleDBSelectorBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  @GetMapping("/dataobj")
  public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

    SimpleTableDto data = simpleTableService.getByDataObjId(id);
    GetSimpleTableBo result = new GetSimpleTableBo(data);

    return InvokeResultBuilder.success(result);
  }

  @PostMapping("/create")
  public InvokeResult create(@Valid CreateSimpleTableVo vo) {

    String tableId = simpleTableService.create(vo);
    return InvokeResultBuilder.success(tableId);
  }
}
