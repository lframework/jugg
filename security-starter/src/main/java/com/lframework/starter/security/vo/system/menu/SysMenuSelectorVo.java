package com.lframework.starter.security.vo.system.menu;

import com.lframework.starter.security.enums.system.SysMenuDisplay;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import java.io.Serializable;

@Data
public class SysMenuSelectorVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    @IsEnum(message = "类型格式不正确！", enumClass = SysMenuDisplay.class)
    private Integer display;
}
