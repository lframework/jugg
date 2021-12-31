package com.lframework.starter.security.bo.system.position;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lframework.common.constants.StringPool;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.common.utils.StringUtil;
import com.lframework.starter.security.dto.system.dept.DefaultSysDeptDto;
import com.lframework.starter.security.dto.system.position.DefaultSysPositionDto;
import com.lframework.starter.security.service.system.ISysDeptService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.UserDto;
import com.lframework.starter.web.service.IUserService;
import com.lframework.starter.web.utils.ApplicationUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class QuerySysPositionBo extends BaseBo<DefaultSysPositionDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 岗位编号
     */
    private String code;

    /**
     * 岗位名称
     */
    private String name;

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

    public QuerySysPositionBo() {

    }

    public QuerySysPositionBo(DefaultSysPositionDto dto) {

        super(dto);
    }

    @Override
    protected void afterInit(DefaultSysPositionDto dto) {

        IUserService userService = ApplicationUtil.getBean(IUserService.class);

        UserDto createBy = userService.getById(this.getCreateBy());
        UserDto updateBy = userService.getById(this.getUpdateBy());
        this.setCreateBy(createBy.getName());
        this.setUpdateBy(updateBy.getName());
    }
}
