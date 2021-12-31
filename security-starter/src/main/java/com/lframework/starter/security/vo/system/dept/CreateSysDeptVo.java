package com.lframework.starter.security.vo.system.dept;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class CreateSysDeptVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 编号
     */
    @NotBlank(message = "编号不能为空！")
    private String code;

    /**
     * 名称
     */
    @NotBlank(message = "名称不能为空！")
    private String name;

    /**
     * 简称
     */
    @NotBlank(message = "简称不能为空！")
    private String shortName;

    /**
     * 父级ID
     */
    private String parentId;

    /**
     * 备注
     */
    private String description;
}
