package com.lframework.starter.security.bo.system.dept;

import com.lframework.starter.security.dto.system.dept.DefaultSysDeptDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysDeptTreeBo extends BaseBo<DefaultSysDeptDto> {

    /**
     * ID
     */
    private String id;

    /**
     * 名称
     */
    private String name;

    /**
     * 父级ID
     */
    private String parentId;

    /**
     * 状态
     */
    private Boolean available;

    public SysDeptTreeBo() {

    }

    public SysDeptTreeBo(DefaultSysDeptDto dto) {

        super(dto);
    }
}
