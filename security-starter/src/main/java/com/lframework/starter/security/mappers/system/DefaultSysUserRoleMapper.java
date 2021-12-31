package com.lframework.starter.security.mappers.system;

import com.lframework.starter.mybatis.mapper.BaseMapper;
import com.lframework.starter.security.dto.system.role.DefaultSysUserRoleDto;
import com.lframework.starter.security.entity.DefaultSysUserRole;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zmj
 * @since 2021-07-04
 */
public interface DefaultSysUserRoleMapper extends BaseMapper<DefaultSysUserRole> {

    /**
     * 根据用户ID查询
     * @param userId
     * @return
     */
    List<DefaultSysUserRoleDto> getByUserId(String userId);
}
