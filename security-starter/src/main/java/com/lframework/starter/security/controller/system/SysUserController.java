package com.lframework.starter.security.controller.system;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.mybatis.utils.PageResultUtil;
import com.lframework.starter.security.bo.system.user.GetSysUserBo;
import com.lframework.starter.security.bo.system.user.QuerySysUserBo;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.security.dto.system.user.DefaultSysUserDto;
import com.lframework.starter.security.service.system.ISysUserService;
import com.lframework.starter.security.vo.system.user.CreateSysUserVo;
import com.lframework.starter.security.vo.system.user.QuerySysUserVo;
import com.lframework.starter.security.vo.system.user.UpdateSysUserVo;
import com.lframework.starter.web.resp.InvokeResult;
import com.lframework.starter.web.resp.InvokeResultBuilder;
import com.lframework.starter.web.service.IUserService;
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
 * 用户管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/system/user")
@ConditionalOnProperty(value = "default-setting.sys-function.enabled", matchIfMissing = true)
public class SysUserController extends DefaultBaseController {

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private IUserService userService;

    /**
     * 用户列表
     */
    @PreAuthorize("@permission.valid('system:user:query','system:user:add','system:user:modify')")
    @GetMapping("/query")
    public InvokeResult query(@Valid QuerySysUserVo vo) {

        PageResult<DefaultSysUserDto> pageResult = sysUserService.query(getPageIndex(vo), getPageSize(vo), vo);

        List<DefaultSysUserDto> datas = pageResult.getDatas();

        if (!CollectionUtil.isEmpty(datas)) {
            List<QuerySysUserBo> results = datas.stream().map(QuerySysUserBo::new).collect(Collectors.toList());

            PageResultUtil.rebuild(pageResult, results);
        }

        return InvokeResultBuilder.success(pageResult);
    }

    /**
     * 查询用户
     */
    @PreAuthorize("@permission.valid('system:user:query','system:user:add','system:user:modify')")
    @GetMapping
    public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

        DefaultSysUserDto data = sysUserService.getById(id);
        if (data == null) {
            throw new DefaultClientException("用户不存在！");
        }

        GetSysUserBo result = new GetSysUserBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 批量停用用户
     */
    @PreAuthorize("@permission.valid('system:user:modify')")
    @PatchMapping("/unable/batch")
    public InvokeResult batchUnable(@NotEmpty(message = "请选择需要停用的用户！") @RequestBody List<String> ids) {

        sysUserService.batchUnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 批量启用用户
     */
    @PreAuthorize("@permission.valid('system:user:modify')")
    @PatchMapping("/enable/batch")
    public InvokeResult batchEnable(@NotEmpty(message = "请选择需要启用的用户！") @RequestBody List<String> ids) {

        sysUserService.batchEnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@permission.valid('system:user:add')")
    @PostMapping
    public InvokeResult create(@Valid CreateSysUserVo vo) {

        sysUserService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@permission.valid('system:user:modify')")
    @PutMapping
    public InvokeResult update(@Valid UpdateSysUserVo vo) {

        sysUserService.update(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@permission.valid('system:user:modify')")
    @PatchMapping("unlock")
    public InvokeResult unlock(@NotBlank(message = "ID不能为空！") String id) {
        userService.unlockById(id);

        return InvokeResultBuilder.success();
    }
}
