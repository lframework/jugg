package com.lframework.starter.security.controller.system;

import com.lframework.common.exceptions.impl.DefaultClientException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.starter.security.bo.system.dept.GetSysDeptBo;
import com.lframework.starter.security.bo.system.dept.SysDeptTreeBo;
import com.lframework.starter.security.controller.DefaultBaseController;
import com.lframework.starter.security.dto.system.dept.DefaultSysDeptDto;
import com.lframework.starter.security.service.system.ISysDeptService;
import com.lframework.starter.security.vo.system.dept.CreateSysDeptVo;
import com.lframework.starter.security.vo.system.dept.UpdateSysDeptVo;
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
 * 部门管理
 *
 * @author zmj
 */
@Validated
@RestController
@RequestMapping("/system/dept")
@ConditionalOnProperty(value = "default-setting.sys-function.enabled", matchIfMissing = true)
public class SysDeptController extends DefaultBaseController {

    @Autowired
    private ISysDeptService sysDeptService;

    /**
     * 部门树形菜单数据
     */
    @PreAuthorize("@permission.valid('system:dept:query','system:dept:add','system:dept:modify')")
    @GetMapping("/trees")
    public InvokeResult trees() {

        List<DefaultSysDeptDto> datas = sysDeptService.selector();
        if (CollectionUtil.isEmpty(datas)) {
            return InvokeResultBuilder.success();
        }

        List<SysDeptTreeBo> results = datas.stream().map(SysDeptTreeBo::new).collect(Collectors.toList());

        return InvokeResultBuilder.success(results);
    }

    /**
     * 查询部门
     */
    @PreAuthorize("@permission.valid('system:dept:query','system:dept:add','system:dept:modify')")
    @GetMapping
    public InvokeResult get(@NotBlank(message = "ID不能为空！") String id) {

        DefaultSysDeptDto data = sysDeptService.getById(id);
        if (data == null) {
            throw new DefaultClientException("部门不存在！");
        }

        GetSysDeptBo result = new GetSysDeptBo(data);

        return InvokeResultBuilder.success(result);
    }

    /**
     * 批量停用部门
     */
    @PreAuthorize("@permission.valid('system:dept:modify')")
    @PatchMapping("/unable/batch")
    public InvokeResult batchUnable(@NotEmpty(message = "请选择需要停用的部门！") @RequestBody List<String> ids) {

        sysDeptService.batchUnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 批量启用部门
     */
    @PreAuthorize("@permission.valid('system:dept:modify')")
    @PatchMapping("/enable/batch")
    public InvokeResult batchEnable(@NotEmpty(message = "请选择需要启用的部门！") @RequestBody List<String> ids) {

        sysDeptService.batchEnable(ids);
        return InvokeResultBuilder.success();
    }

    /**
     * 新增部门
     */
    @PreAuthorize("@permission.valid('system:dept:add')")
    @PostMapping
    public InvokeResult create(@Valid CreateSysDeptVo vo) {

        sysDeptService.create(vo);

        return InvokeResultBuilder.success();
    }

    /**
     * 修改部门
     */
    @PreAuthorize("@permission.valid('system:dept:modify')")
    @PutMapping
    public InvokeResult update(@Valid UpdateSysDeptVo vo) {

        sysDeptService.update(vo);

        return InvokeResultBuilder.success();
    }
}
