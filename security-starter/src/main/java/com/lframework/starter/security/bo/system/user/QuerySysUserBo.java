package com.lframework.starter.security.bo.system.user;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.mybatis.dto.system.dept.DefaultSysDeptDto;
import com.lframework.starter.mybatis.dto.system.dept.DefaultSysUserDeptDto;
import com.lframework.starter.mybatis.dto.system.position.DefaultSysPositionDto;
import com.lframework.starter.mybatis.dto.system.position.DefaultSysUserPositionDto;
import com.lframework.starter.mybatis.dto.system.role.DefaultSysRoleDto;
import com.lframework.starter.mybatis.dto.system.role.DefaultSysUserRoleDto;
import com.lframework.starter.mybatis.dto.system.user.DefaultSysUserDto;
import com.lframework.starter.mybatis.service.IUserService;
import com.lframework.starter.mybatis.service.system.ISysDeptService;
import com.lframework.starter.mybatis.service.system.ISysPositionService;
import com.lframework.starter.mybatis.service.system.ISysRoleService;
import com.lframework.starter.mybatis.service.system.ISysUserDeptService;
import com.lframework.starter.mybatis.service.system.ISysUserPositionService;
import com.lframework.starter.mybatis.service.system.ISysUserRoleService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.utils.ApplicationUtil;
import io.swagger.annotations.ApiModelProperty;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySysUserBo extends BaseBo<DefaultSysUserDto> {

  /**
   * ID
   */
  @ApiModelProperty("ID")
  private String id;

  /**
   * 编号
   */
  @ApiModelProperty("编号")
  private String code;

  /**
   * 姓名
   */
  @ApiModelProperty("姓名")
  private String name;

  /**
   * 岗位名称
   */
  @ApiModelProperty("岗位名称")
  private String positionName;

  /**
   * 部门名称
   */
  @ApiModelProperty("部门名称")
  private String deptName;

  /**
   * 角色名称
   */
  @ApiModelProperty("角色名称")
  private String roleName;

  /**
   * 用户名
   */
  @ApiModelProperty("用户名")
  private String username;

  /**
   * 邮箱
   */
  @ApiModelProperty("邮箱")
  private String email;

  /**
   * 联系电话
   */
  @ApiModelProperty("联系电话")
  private String telephone;

  /**
   * 性别
   */
  @ApiModelProperty("性别")
  private Integer gender;

  /**
   * 状态
   */
  @ApiModelProperty("状态")
  private Boolean available;

  /**
   * 是否锁定
   */
  @ApiModelProperty("是否锁定")
  private Boolean lockStatus;

  /**
   * 备注
   */
  @ApiModelProperty("备注")
  private String description;

  /**
   * 创建人
   */
  @ApiModelProperty("创建人")
  private String createBy;

  /**
   * 创建时间
   */
  @ApiModelProperty("创建时间")
  @JsonFormat(pattern = StringPool.DATE_TIME_PATTERN)
  private LocalDateTime createTime;

  /**
   * 修改人
   */
  @ApiModelProperty("修改人")
  private String updateBy;

  /**
   * 修改时间
   */
  @ApiModelProperty("修改时间")
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

    ISysUserPositionService sysUserPositionService = ApplicationUtil
        .getBean(ISysUserPositionService.class);
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
