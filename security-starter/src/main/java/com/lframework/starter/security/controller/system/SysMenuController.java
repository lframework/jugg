package com.lframework.starter.security.controller.system;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.exceptions.impl.InputErrorException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.dto.system.menu.DefaultSysMenuDto;
import com.lframework.starter.mybatis.enums.system.SysMenuDisplay;
import com.lframework.starter.mybatis.service.system.ISysMenuService;
import com.lframework.starter.mybatis.vo.system.menu.CreateSysMenuVo;
import com.lframework.starter.mybatis.vo.system.menu.UpdateSysMenuVo;
import com.lframework.starter.security.bo.system.menu.GetSysMenuBo;
import com.lframework.starter.security.bo.system.menu.QuerySysMenuBo;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.utils.EnumUtil;
import com.lframework.web.common.security.SecurityUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ??????????????????
 *
 * @author zmj
 */
@Api(tags = "??????????????????")
@Validated
@RestController
@RequestMapping("/system/menu")
public class SysMenuController extends DefaultBaseController {

  @Autowired
  private ISysMenuService sysMenuService;

  /**
   * ??????????????????
   */
  @ApiOperation("??????????????????")
  @PreAuthorize("@permission.valid('system:menu:query', 'system:menu:add')")
  @GetMapping("/query")
  public InvokeResult<List<QuerySysMenuBo>> query() {

    List<QuerySysMenuBo> results = Collections.EMPTY_LIST;
    List<DefaultSysMenuDto> datas = sysMenuService.queryList();
    if (CollectionUtil.isNotEmpty(datas)) {
      results = datas.stream().map(QuerySysMenuBo::new).collect(Collectors.toList());
    }

    return InvokeResultBuilder.success(results);
  }

  /**
   * ??????????????????
   */
  @ApiOperation("??????????????????")
  @PreAuthorize("@permission.valid('system:menu:add')")
  @PostMapping
  public InvokeResult<Void> add(@Valid CreateSysMenuVo vo) {

    this.validVo(vo);

    sysMenuService.create(vo);

    return InvokeResultBuilder.success();
  }

  /**
   * ??????????????????
   */
  @ApiOperation("??????????????????")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('system:menu:query', 'system:menu:add', 'system:menu:modify')")
  @GetMapping
  public InvokeResult<GetSysMenuBo> get(@NotBlank(message = "ID???????????????") String id) {

    DefaultSysMenuDto data = sysMenuService.findById(id);
    if (ObjectUtil.isNull(data)) {
      throw new DefaultClientException("??????????????????");
    }

    return InvokeResultBuilder.success(new GetSysMenuBo(data));
  }

  /**
   * ??????????????????
   */
  @ApiOperation("??????????????????")
  @PreAuthorize("@permission.valid('system:menu:modify')")
  @PutMapping
  public InvokeResult<Void> modify(@Valid UpdateSysMenuVo vo) {

    this.validVo(vo);

    sysMenuService.update(vo);

    sysMenuService.cleanCacheByKey(vo.getId());

    return InvokeResultBuilder.success();
  }

  /**
   * ??????ID??????
   */
  @ApiOperation("??????ID??????")
  @ApiImplicitParam(value = "ID", name = "id", paramType = "query", required = true)
  @PreAuthorize("@permission.valid('system:menu:delete')")
  @DeleteMapping
  public InvokeResult<Void> delete(@NotBlank(message = "ID???????????????") String id) {

    sysMenuService.deleteById(id);

    sysMenuService.cleanCacheByKey(id);

    return InvokeResultBuilder.success();
  }

  /**
   * ????????????
   */
  @ApiOperation("????????????")
  @PreAuthorize("@permission.valid('system:menu:modify')")
  @PatchMapping("/enable/batch")
  public InvokeResult<Void> batchEnable(
      @ApiParam(value = "??????ID", required = true) @NotEmpty(message = "?????????????????????????????????") @RequestBody List<String> ids) {

    sysMenuService.batchEnable(ids, SecurityUtil.getCurrentUser().getId());

    for (String id : ids) {
      sysMenuService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  /**
   * ????????????
   */
  @ApiOperation("????????????")
  @PreAuthorize("@permission.valid('system:menu:modify')")
  @PatchMapping("/unable/batch")
  public InvokeResult<Void> batchUnable(
      @ApiParam(value = "??????ID", required = true) @NotEmpty(message = "?????????????????????????????????") @RequestBody List<String> ids) {

    sysMenuService.batchUnable(ids, SecurityUtil.getCurrentUser().getId());

    for (String id : ids) {
      sysMenuService.cleanCacheByKey(id);
    }

    return InvokeResultBuilder.success();
  }

  private void validVo(CreateSysMenuVo vo) {

    SysMenuDisplay sysMenuDisplay = EnumUtil.getByCode(SysMenuDisplay.class, vo.getDisplay());

    if (sysMenuDisplay == SysMenuDisplay.CATALOG || sysMenuDisplay == SysMenuDisplay.FUNCTION) {
      if (StringUtil.isBlank(vo.getName())) {
        throw new InputErrorException("????????????????????????");
      }

      if (StringUtil.isBlank(vo.getPath())) {
        throw new InputErrorException("????????????????????????");
      }

      if (ObjectUtil.isNull(vo.getHidden())) {
        throw new InputErrorException("????????????????????????");
      }

      if (sysMenuDisplay == SysMenuDisplay.FUNCTION) {
        if (StringUtil.isBlank(vo.getComponent())) {
          throw new InputErrorException("??????????????????");
        }
        if (ObjectUtil.isNull(vo.getNoCache())) {
          throw new InputErrorException("???????????????????????????");
        }

        if (!StringUtil.isBlank(vo.getParentId())) {
          DefaultSysMenuDto parentMenu = sysMenuService.findById(vo.getParentId());

          if (parentMenu.getDisplay() != SysMenuDisplay.CATALOG) {
            throw new InputErrorException(
                "?????????????????????" + SysMenuDisplay.FUNCTION.getDesc() + "???????????????????????????????????????"
                    + SysMenuDisplay.CATALOG.getDesc() + "??????");
          }
        }
      }
    } else if (sysMenuDisplay == SysMenuDisplay.PERMISSION) {
      if (StringUtil.isBlank(vo.getParentId())) {
        throw new InputErrorException(
            "?????????????????????" + SysMenuDisplay.PERMISSION.getDesc() + "????????????????????????????????????");
      }

      DefaultSysMenuDto parentMenu = sysMenuService.findById(vo.getParentId());
      if (ObjectUtil.isNull(parentMenu)) {
        throw new InputErrorException(
            "?????????????????????" + SysMenuDisplay.PERMISSION.getDesc() + "????????????????????????????????????");
      }

      if (parentMenu.getDisplay() != SysMenuDisplay.FUNCTION) {
        throw new InputErrorException(
            "?????????????????????" + SysMenuDisplay.PERMISSION.getDesc() + "???????????????????????????????????????"
                + SysMenuDisplay.FUNCTION.getDesc() + "??????");
      }
      if (StringUtil.isBlank(vo.getPermission())) {
        throw new InputErrorException("??????????????????");
      }
    }
  }
}
