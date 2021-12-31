package com.lframework.starter.security.bo.system.position;

import com.lframework.common.utils.StringUtil;
import com.lframework.starter.security.dto.system.position.DefaultSysPositionDto;
import com.lframework.starter.security.service.system.ISysDeptService;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.utils.ApplicationUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSysPositionBo extends BaseBo<DefaultSysPositionDto> {

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

    public GetSysPositionBo() {

    }

    public GetSysPositionBo(DefaultSysPositionDto dto) {

        super(dto);
    }

    @Override
    protected void afterInit(DefaultSysPositionDto dto) {

    }
}
