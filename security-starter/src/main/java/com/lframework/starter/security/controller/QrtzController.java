package com.lframework.starter.security.controller;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.dto.qrtz.QrtzDto;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.service.IQrtzService;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.mybatis.vo.qrtz.CreateQrtzVo;
import com.lframework.starter.mybatis.vo.qrtz.QueryQrtzVo;
import com.lframework.starter.mybatis.vo.qrtz.UpdateQrtzVo;
import com.lframework.starter.security.bo.qrtz.GetQrtzBo;
import com.lframework.starter.security.bo.qrtz.QueryQrtzBo;
import com.lframework.starter.web.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "定时器管理")
@RestController
@RequestMapping("/qrtz")
public class QrtzController extends DefaultBaseController {

  @Autowired
  private IQrtzService qrtzService;

  /**
   * 查询列表
   *
   * @param vo
   * @return
   */
  @ApiOperation("查询列表")
  @PreAuthorize("@permission.valid('development:qrtz:manage')")
  @GetMapping("/query")
  public InvokeResult<PageResult<QueryQrtzBo>> query(@Valid QueryQrtzVo vo) {

    PageResult<QrtzDto> pageResult = qrtzService.query(getPageIndex(vo), getPageSize(vo), vo);
    List<QrtzDto> datas = pageResult.getDatas();

    List<QueryQrtzBo> results = null;
    if (!CollectionUtil.isEmpty(datas)) {
      results = datas.stream().map(QueryQrtzBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(PageResultUtil.rebuild(pageResult, results));
  }

  /**
   * 查询
   *
   * @return
   */
  @ApiOperation("查询")
  @PreAuthorize("@permission.valid('development:qrtz:manage')")
  @GetMapping
  public InvokeResult<GetQrtzBo> get(@NotBlank(message = "名称不能为空！") String name,
      @NotBlank(message = "组不能为空！") String group) {
    QrtzDto data = qrtzService.findById(name, group);
    if (data == null) {
      throw new DefaultClientException("任务不存在！");
    }

    return InvokeResultBuilder.success(new GetQrtzBo(data));
  }

  /**
   * 创建
   *
   * @param vo
   * @return
   */
  @ApiOperation("创建")
  @PreAuthorize("@permission.valid('development:qrtz:manage')")
  @PostMapping
  public InvokeResult<Void> create(@Valid @RequestBody CreateQrtzVo vo) {
    qrtzService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 修改
   *
   * @param vo
   * @return
   */
  @ApiOperation("修改")
  @PreAuthorize("@permission.valid('development:qrtz:manage')")
  @PutMapping
  public InvokeResult<Void> update(@Valid @RequestBody UpdateQrtzVo vo) {
    qrtzService.update(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * 暂停
   *
   * @return
   */
  @ApiOperation("暂停")
  @PreAuthorize("@permission.valid('development:qrtz:manage')")
  @PutMapping("/pause")
  public InvokeResult<Void> pause(@NotBlank(message = "名称不能为空！") String name,
      @NotBlank(message = "组不能为空！") String group) {
    qrtzService.pause(name, group);

    return InvokeResultBuilder.success();
  }

  /**
   * 恢复
   *
   * @return
   */
  @ApiOperation("恢复")
  @PreAuthorize("@permission.valid('development:qrtz:manage')")
  @PutMapping("/resume")
  public InvokeResult<Void> resume(@NotBlank(message = "名称不能为空！") String name,
      @NotBlank(message = "组不能为空！") String group) {
    qrtzService.resume(name, group);

    return InvokeResultBuilder.success();
  }

  /**
   * 触发
   *
   * @return
   */
  @ApiOperation("触发")
  @PreAuthorize("@permission.valid('development:qrtz:manage')")
  @PutMapping("/trigger")
  public InvokeResult<Void> trigger(@NotBlank(message = "名称不能为空！") String name,
      @NotBlank(message = "组不能为空！") String group) {
    qrtzService.trigger(name, group);

    return InvokeResultBuilder.success();
  }

  /**
   * 删除
   *
   * @return
   */
  @ApiOperation("删除")
  @PreAuthorize("@permission.valid('development:qrtz:manage')")
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "名称不能为空！") String name,
      @NotBlank(message = "组不能为空！") String group) {
    qrtzService.delete(name, group);

    return InvokeResultBuilder.success();
  }
}
