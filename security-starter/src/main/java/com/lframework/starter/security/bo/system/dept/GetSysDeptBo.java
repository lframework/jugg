package com.lframework.starter.security.bo.system.dept;

import com.lframework.starter.security.dto.system.dept.DefaultSysDeptDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class GetSysDeptBo extends BaseBo<DefaultSysDeptDto> {

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
     * 父级ID
     */
    private String parentId;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 状态
     */
    private Boolean available;

    /**
     * 备注
     */
    private String description;

    public GetSysDeptBo() {

    }

    public GetSysDeptBo(DefaultSysDeptDto dto) {

        super(dto);
    }
}
