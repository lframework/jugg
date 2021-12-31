package com.lframework.starter.security.controller.system;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.bo.system.role.GetSysRoleBo;
import com.lframework.starter.security.bo.system.role.QuerySysRoleBo;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.security.dto.system.role.DefaultSysRoleDto;
import com.lframework.starter.security.service.system.ISysRoleService;
import com.lframework.starter.security.vo.system.role.CreateSysRoleVo;
import com.lframework.starter.security.vo.system.role.QuerySysRoleVo;
import com.lframework.starter.security.vo.system.role.UpdateSysRoleVo;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/system/role")
@ConditionalOnProperty(value = "default-setting.sys-function.enabled", matchIfMissing = true)
public class SysRoleController extends DefaultBaseController {

    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * 角色列表
     */
    @PreAuthorize("@permission.valid('system:role:query','system:role:add','system:role:modify')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QuerySysRoleVo vo) {

        PageResult<DefaultSysRoleDto> pageResult = sysRoleService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<DefaultSysRoleDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QuerySysRoleBo> results = datas.stream().map(QuerySysRoleBo::new).collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 查询角色
     */
    @PreAuthorize("@permission.valid('system:role:query','system:role:add','system:role:modify')")
    @GetMapping
    public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

        DefaultSysRoleDto data = sysRoleService.getById(id);
        if (data == null) {
            throw new DefaultClientException("角色不存在！");
        }

        GetSysRoleBo result = new GetSysRoleBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 批量停用角色
     */
    @PreAuthorize("@permission.valid('system:role:modify')")
    @PatchMapping("/unable/batch")
    public InvokeResult batchUnable(@NotEmpty(message = "请选择需要停用的角色！") @RequestBody List<String> ids) {

        sysRoleService.batchUnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 批量启用角色
     */
    @PreAuthorize("@permission.valid('system:role:modify')")
    @PatchMapping("/enable/batch")
    public InvokeResult batchEnable(@NotEmpty(message = "请选择需要启用的角色！") @RequestBody List<String> ids) {

        sysRoleService.batchEnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 新增角色
     */
    @PreAuthorize("@permission.valid('system:role:add')")
    @PostMapping
    public InvokeResult create(@Valid CreateSysRoleVo vo) {

        sysRoleService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改角色
     */
    @PreAuthorize("@permission.valid('system:role:modify')")
    @PutMapping
    public InvokeResult update(@Valid UpdateSysRoleVo vo) {

        sysRoleService.update(vo);

        return InvokeResultBuilder.success();
    }
}
