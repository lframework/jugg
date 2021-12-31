package com.lframework.starter.security.service.system;

import com.lframework.starter.security.dto.system.dept.DefaultSysDeptDto;
import com.lframework.starter.security.vo.system.dept.CreateSysDeptVo;
import com.lframework.starter.security.vo.system.dept.UpdateSysDeptVo;
import com.lframework.starter.web.service.BaseService;

import java.util.Collection;
import java.util.List;

public interface ISysDeptService extends BaseService {

    /**
     * 选择器
     * @return
     */
    List<DefaultSysDeptDto> selector();

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    DefaultSysDeptDto getById(String id);

    /**
     * 根据ID停用
     * @param ids
     */
    void batchUnable(Collection<String> ids);

    /**
     * 根据ID启用
     * @param ids
     */
    void batchEnable(Collection<String> ids);

    /**
     * 创建
     * @param vo
     * @return
     */
    String create(CreateSysDeptVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdateSysDeptVo vo);
}
