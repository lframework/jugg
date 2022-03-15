package com.lframework.starter.security.bo.system.user;

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
import com.lframework.starter.web.utils.ApplicationUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.stream.Collectors;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSysUserBo extends BaseBo<DefaultSysUserDto> {

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
     * 岗位
     */
    private List<PositionBo> positions;

    /**
     * 岗位名称
     */
    private String positionName;

    /**
     * 部门
     */
    private List<DeptBo> depts;

    /**
     * 部门名称
     */
    private String deptName;


    /**
     * 角色
     */
    private List<RoleBo> roles;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 状态 1-在用 0停用
     */
    private Boolean available;

    /**
     * 是否锁定
     */
    private Boolean lockStatus;

    /**
     * 备注
     */
    private String description;

    public GetSysUserBo() {

    }

    public GetSysUserBo(DefaultSysUserDto dto) {

        super(dto);
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class PositionBo extends BaseBo {

        /**
         * 岗位ID
         */
        private String id;

        /**
         * 岗位名称
         */
        private String name;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class DeptBo extends BaseBo {

        /**
         * 部门ID
         */
        private String id;

        /**
         * 部门名称
         */
        private String name;
    }

    @Data
    @EqualsAndHashCode(callSuper = true)
    public static class RoleBo extends BaseBo {

        /**
         * 角色ID
         */
        private String id;

        /**
         * 角色名称
         */
        private String name;
    }

    @Override
    protected void afterInit(DefaultSysUserDto dto) {

        ISysUserPositionService sysUserPositionService = ApplicationUtil.getBean(ISysUserPositionService.class);
        List<DefaultSysUserPositionDto> userPositions = sysUserPositionService.getByUserId(dto.getId());
        if (!CollectionUtil.isEmpty(userPositions)) {
            ISysPositionService sysPositionService = ApplicationUtil.getBean(ISysPositionService.class);
            this.positions = userPositions.stream().map(t -> {
                DefaultSysPositionDto position = sysPositionService.getById(t.getPositionId());
                PositionBo positionBo = new PositionBo();
                positionBo.setId(position.getId());
                positionBo.setName(position.getName());

                return positionBo;
            }).collect(Collectors.toList());

            this.positionName = StringUtil.join(StringPool.STR_SPLIT_CN, this.positions.stream().map(PositionBo::getName).collect(
                    Collectors.toList()));
        }

        ISysUserDeptService sysUserDeptService = ApplicationUtil.getBean(ISysUserDeptService.class);
        List<DefaultSysUserDeptDto> userDepts = sysUserDeptService.getByUserId(dto.getId());
        if (!CollectionUtil.isEmpty(userDepts)) {
            ISysDeptService sysDeptService = ApplicationUtil.getBean(ISysDeptService.class);
            this.depts = userDepts.stream().map(t -> {
                DefaultSysDeptDto dept = sysDeptService.getById(t.getDeptId());
                DeptBo deptBo = new DeptBo();
                deptBo.setId(dept.getId());
                deptBo.setName(dept.getName());

                return deptBo;
            }).collect(Collectors.toList());

            this.deptName = StringUtil.join(StringPool.STR_SPLIT_CN, this.depts.stream().map(DeptBo::getName).collect(
                    Collectors.toList()));
        }

        ISysUserRoleService sysUserRoleService = ApplicationUtil.getBean(ISysUserRoleService.class);
        List<DefaultSysUserRoleDto> userRoles = sysUserRoleService.getByUserId(dto.getId());
        if (!CollectionUtil.isEmpty(userRoles)) {
            ISysRoleService sysRoleService = ApplicationUtil.getBean(ISysRoleService.class);
            this.roles = userRoles.stream().map(t -> {
                DefaultSysRoleDto role = sysRoleService.getById(t.getRoleId());
                RoleBo roleBo = new RoleBo();
                roleBo.setId(role.getId());
                roleBo.setName(role.getName());

                return roleBo;
            }).collect(Collectors.toList());

            this.roleName = StringUtil.join(StringPool.STR_SPLIT_CN, this.roles.stream().map(RoleBo::getName).collect(
                    Collectors.toList()));
        }
    }
}
