package com.lframework.starter.security.bo.system.menu;

import com.lframework.starter.security.dto.system.menu.DefaultSysMenuDto;
import com.lframework.starter.web.bo.BaseBo;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SysMenuSelectorBo extends BaseBo<DefaultSysMenuDto> {

    private static final long serialVersionUID = 1L;

    private String id;

    private String title;

    private String parentId;

    public SysMenuSelectorBo() {

    }

    public SysMenuSelectorBo(DefaultSysMenuDto dto) {

        super(dto);
    }
}
