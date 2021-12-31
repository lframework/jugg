package com.lframework.starter.security.service.system;

import com.lframework.starter.security.dto.system.dept.DefaultSysUserDeptDto;
import com.lframework.starter.security.dto.system.position.DefaultSysUserPositionDto;
import com.lframework.starter.security.vo.system.dept.SysUserDeptSettingVo;
import com.lframework.starter.security.vo.system.position.SysUserPositionSettingVo;
import com.lframework.starter.web.service.BaseService;

import java.util.List;

public interface ISysUserDeptService extends BaseService {

    /**
     * 设置部门
     * @param vo
     */
    void setting(SysUserDeptSettingVo vo);

    /**
     * 根据用户ID查询
     * @param userId
     * @return
     */
    List<DefaultSysUserDeptDto> getByUserId(String userId);

    /**
     * 根据部门ID查询是否存在
     * @param deptId
     * @return
     */
    Boolean hasByDeptId(String deptId);
}
