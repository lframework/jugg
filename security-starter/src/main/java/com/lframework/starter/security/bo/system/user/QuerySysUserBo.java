package com.lframework.starter.security.bo.system.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.security.dto.system.dept.DefaultSysDeptDto;
import com.lframework.starter.security.dto.system.dept.DefaultSysUserDeptDto;
import com.lframework.starter.security.dto.system.position.DefaultSysPositionDto;
import com.lframework.starter.security.dto.system.position.DefaultSysUserPositionDto;
import com.lframework.starter.security.dto.system.role.DefaultSysRoleDto;
import com.lframework.starter.security.dto.system.role.DefaultSysUserRoleDto;
import com.lframework.starter.security.dto.system.user.DefaultSysUserDto;
import com.lframework.starter.security.service.system.*;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySysUserBo extends BaseBo<DefaultSysUserDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 编号
     */
    private String code;

    /**
     * 姓名
     */
    private String name;

    /**
     * 岗位名称
     */
    private String positionName;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 用户名
     */
    private String username;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 联系电话
     */
    private String telephone;

    /**
     * 性别 0-未知 1-男 2-女
     */
    private Integer gender;

    /**
     * 状态 1-在用 0停用
     */
    private Boolean available;

    /**
     * 备注
     */
    private String description;

    /**
     * 创建人ID
     */
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime createTime;

    /**
     * 修改人ID
     */
    private String updateBy;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
    private LocalDateTime updateTime;

    public QuerySysUserBo() {

    }

    public QuerySysUserBo(DefaultSysUserDto dto) {

        super(dto);
    }

    @Override
    protected void afterInit(DefaultSysUserDto dto) {

        IUserService userService = ApplicationUtil.getBean(IUserService.class);

        UserDto createBy = userService.getById(this.getCreateBy());
        UserDto updateBy = userService.getById(this.getUpdateBy());
        this.setCreateBy(createBy.getName());
        this.setUpdateBy(updateBy.getName());

        ISysUserPositionService sysUserPositionService = ApplicationUtil.getBean(ISysUserPositionService.class);
        List<DefaultSysUserPositionDto> userPositions = sysUserPositionService.getByUserId(dto.getId());
        if (!CollectionUtil.isEmpty(userPositions)) {
            ISysPositionService sysPositionService = ApplicationUtil.getBean(ISysPositionService.class);
            List<String> positionNames = new ArrayList<>(userPositions.size());
            for (DefaultSysUserPositionDto userPosition : userPositions) {
                DefaultSysPositionDto position = sysPositionService.getById(userPosition.getPositionId());
                positionNames.add(position.getName());
            }

            this.positionName = StringUtil.join(StringPool.STR_SPLIT_CN, positionNames);
        }

        ISysUserDeptService sysUserDeptService = ApplicationUtil.getBean(ISysUserDeptService.class);
        List<DefaultSysUserDeptDto> userDepts = sysUserDeptService.getByUserId(dto.getId());
        if (!CollectionUtil.isEmpty(userDepts)) {
            ISysDeptService sysDeptService = ApplicationUtil.getBean(ISysDeptService.class);
            List<String> deptNames = new ArrayList<>(userDepts.size());
            for (DefaultSysUserDeptDto userDept : userDepts) {
                DefaultSysDeptDto dept = sysDeptService.getById(userDept.getDeptId());
                deptNames.add(dept.getName());
            }

            this.deptName = StringUtil.join(StringPool.STR_SPLIT_CN, deptNames);
        }

        ISysUserRoleService sysUserRoleService = ApplicationUtil.getBean(ISysUserRoleService.class);
        List<DefaultSysUserRoleDto> userRoles = sysUserRoleService.getByUserId(dto.getId());
        if (!CollectionUtil.isEmpty(userRoles)) {
            ISysRoleService sysRoleService = ApplicationUtil.getBean(ISysRoleService.class);
            List<String> roleNames = new ArrayList<>(userRoles.size());
            for (DefaultSysUserRoleDto userRole : userRoles) {
                DefaultSysRoleDto role = sysRoleService.getById(userRole.getRoleId());
                roleNames.add(role.getName());
            }

            this.roleName = StringUtil.join(StringPool.STR_SPLIT_CN, roleNames);
        }
    }
}
