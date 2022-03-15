package com.lframework.starter.security.service.system;

import com.lframework.starter.mybatis.resp.PageResult;
import com.lframework.starter.security.dto.system.user.DefaultSysUserDto;
import com.lframework.starter.security.vo.system.user.*;
import com.lframework.starter.web.service.BaseService;

import java.util.List;

public interface ISysUserService extends BaseService {

    /**
     * 查询列表
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<DefaultSysUserDto> query(Integer pageIndex, Integer pageSize, QuerySysUserVo vo);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    DefaultSysUserDto getById(String id);

    /**
     * 批量启用
     * @param ids
     */
    void batchEnable(List<String> ids);

    /**
     * 批量停用
     * @param ids
     */
    void batchUnable(List<String> ids);

    /**
     * 创建
     * @param vo
     * @return
     */
    String create(CreateSysUserVo vo);

    /**
     * 修改
     * @param vo
     */
    void update(UpdateSysUserVo vo);

    /**
     * 选择器
     * @param pageIndex
     * @param pageSize
     * @param vo
     * @return
     */
    PageResult<DefaultSysUserDto> selector(Integer pageIndex, Integer pageSize, SysUserSelectorVo vo);

    /**
     * 注册
     * @param vo
     */
    void regist(RegistUserVo vo);
}
