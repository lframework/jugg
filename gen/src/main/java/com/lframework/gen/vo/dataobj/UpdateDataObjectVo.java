package com.lframework.gen.vo.dataobj;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改数据对象Vo
 */
@Data
public class UpdateDataObjectVo implements BaseVo, Serializable {

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
     * 状态
     */
    @NotNull(message = "请选择状态！")
    private Boolean available;

    /**
     * 备注
     */
    private String description;
}
