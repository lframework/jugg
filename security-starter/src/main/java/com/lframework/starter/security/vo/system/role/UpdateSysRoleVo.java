package com.lframework.starter.security.vo.system.role;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdateSysRoleVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotBlank(message = "ID不能为空！")
    private String id;

    /**
     * 编号
     */
    @NotBlank(message = "请输入编号！")
    private String code;

    /**
     * 名称
     */
    @NotBlank(message = "请输入名称！")
    private String name;

    /**
     * 权限
     */
    private String permission;

    /**
     * 状态
     */
    @NotNull(message = "状态不能为空！")
    private Boolean available;

    /**
     * 备注
     */
    private String description;
}
