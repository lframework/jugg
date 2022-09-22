package com.lframework.starter.security.controller.system;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.entity.SysDataDic;
import com.lframework.starter.mybatis.entity.SysDataDicItem;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.system.ISysDataDicItemService;
import com.lframework.starter.mybatis.service.system.ISysDataDicService;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.system.dic.item.CreateSysDataDicItemVo;
import com.lframework.starter.mybatis.vo.system.dic.item.QuerySysDataDicItemVo;
import com.lframework.starter.mybatis.vo.system.dic.item.UpdateSysDataDicItemVo;
import com.lframework.starter.security.bo.system.dic.item.GetSysDataDicItemBo;
import com.lframework.starter.security.bo.system.dic.item.QuerySysDataDicItemBo;
import com.lframework.starter.security.bo.system.dic.item.SysDataDicItemBo;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据字典值
 *
 * @author zmj
 */
@Api(tags = "数据字典值")
@Validated
@RestController
@RequestMapping("/system/dic/item")
public class SysDataDicItemController extends DefaultBaseController {

  @Autowired
  private ISysDataDicItemService sysDataDicItemService;

  @Autowired
  private ISysDataDicService sysDataDicService;

  /**
   * 查询列表
   */
  @ApiOperation("查询列表")
  @PreAuthorize("@permission.valid('system:dic-item:*')")
  @GetMapping("/query")
  public InvokeResult<PageResult<QuerySysDataDicItemBo>> query(@Valid QuerySysDataDicItemVo vo) {
    PageResult<SysDataDicItem> pageResult = sysDataDicItemService.query(getPageIndex(vo),
        getPageSize(vo), vo);
    List<SysDataDicItem> datas = pageResult.getDatas();
    List<QuerySysDataDicItemBo> results = Collections.EMPTY_LIST;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QuerySysDataDicItemBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 根据ID查询
   */
  @ApiOperation("根据ID查询")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('system:dic-item:*')")
  @GetMapping
  public InvokeResult<GetSysDataDicItemBo> get(@NotBlank(message = "ID不能为空！") String id) {

    SysDataDicItem data = sysDataDicItemService.findById(id);
    if (data == null) {
      throw new DefaultClientException("数据字典值不存在！");
    }

    GetSysDataDicItemBo result = new GetSysDataDicItemBo(data);

    return InvokeResultBuilder.success(result);
  }

  /**
   * 根据字典编号查询
   */
  @ApiOperation("根据字典编号查询")
  @ApiImplicitParam(value = "字典编号", name = "code", paramType = "query", required = true)
  @GetMapping("/bydic")
  public InvokeResult<List<SysDataDicItemBo>> getByDicCode(
      @NotBlank(message = "字典编号不能为空！") String code) {
    List<SysDataDicItem> datas = sysDataDicItemService.findByDicCode(code);

    List<SysDataDicItemBo> results = datas.stream().map(SysDataDicItemBo::new)
        .collect(Collectors.toList());

    return InvokeResultBuilder.success(results);
  }

  /**
   * 新增数据字典值
   */
  @ApiOperation("新增数据字典值")
  @PreAuthorize("@permission.valid('system:dic-item:add')")
  @PostMapping
  public InvokeResult<Void> create(@Valid CreateSysDataDicItemVo vo) {

    sysDataDicItemService.create(vo);

    SysDataDic dic = sysDataDicService.findById(vo.getDicId());
    sysDataDicItemService.cleanCacheByKey(dic.getCode());

    return InvokeResultBuilder.success();
  }

  /**
   * 修改数据字典值
   */
  @ApiOperation("修改数据字典值")
  @PreAuthorize("@permission.valid('system:dic-item:modify')")
  @PutMapping
  public InvokeResult<Void> update(@Valid UpdateSysDataDicItemVo vo) {

    SysDataDicItem item = sysDataDicItemService.findById(vo.getId());

    sysDataDicItemService.update(vo);

    SysDataDic dic = sysDataDicService.findById(item.getDicId());
    sysDataDicItemService.cleanCacheByKey(dic.getCode());

    sysDataDicItemService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * 删除数据字典值
   */
  @ApiOperation("删除数据字典值")
  @PreAuthorize("@permission.valid('system:dic-item:delete')")
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID不能为空！") String id) {

    SysDataDicItem item = sysDataDicItemService.findById(id);

    sysDataDicItemService.deleteById(id);

    SysDataDic dic = sysDataDicService.findById(item.getDicId());
    sysDataDicItemService.cleanCacheByKey(dic.getCode());

    sysDataDicItemService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }
}
