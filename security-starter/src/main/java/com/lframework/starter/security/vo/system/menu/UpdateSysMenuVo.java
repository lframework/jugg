package com.lframework.starter.security.vo.system.menu;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateSysMenuVo extends CreateSysMenuVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotBlank(message = "ID不能为空！")
    private String id;
}
