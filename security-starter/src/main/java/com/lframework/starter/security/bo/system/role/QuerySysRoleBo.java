package com.lframework.starter.security.bo.system.role;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.starter.security.dto.system.role.DefaultSysRoleDto;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySysRoleBo extends BaseBo<DefaultSysRoleDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 编号
     */
    private String code;

    /**
     * 名称
     */
    private String name;

    /**
     * 权限
     */
    private String permission;

    /**
     * 状态
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

    public QuerySysRoleBo() {

    }

    public QuerySysRoleBo(DefaultSysRoleDto dto) {

        super(dto);
    }

    @Override
    protected void afterInit(DefaultSysRoleDto dto) {

        IUserService userService = ApplicationUtil.getBean(IUserService.class);

        UserDto createBy = userService.getById(this.getCreateBy());
        UserDto updateBy = userService.getById(this.getUpdateBy());
        this.setCreateBy(createBy.getName());
        this.setUpdateBy(updateBy.getName());
    }
}
