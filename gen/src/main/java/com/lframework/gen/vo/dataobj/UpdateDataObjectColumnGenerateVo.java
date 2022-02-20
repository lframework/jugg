package com.lframework.gen.vo.dataobj;

import com.lframework.gen.enums.GenDataType;
import com.lframework.gen.enums.GenOrderType;
import com.lframework.gen.enums.GenQueryType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdateDataObjectColumnGenerateVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotBlank(message = "ID不能为空！")
    private String id;

    /**
     * 字段显示名称
     */
    @NotBlank(message = "显示名称不能为空！")
    private String name;

    /**
     * 字段名称
     */
    @NotBlank(message = "属性名不能为空！")
    private String columnName;

    /**
     * 数据类型
     */
    @NotNull(message = "数据类型不能为空！")
    @IsEnum(message = "数据类型格式不正确！", enumClass = GenDataType.class)
    private Integer dataType;

    /**
     * 备注
     */
    private String description;

    /**
     * 显示类型
     */
    @NotNull(message = "显示类型不能为空！")
    @IsEnum(message = "显示类型格式不正确！", enumClass = GenDataType.class)
    private Integer viewType;

    /**
     * 是否内置枚举
     */
    @NotNull(message = "是否内置枚举不能为空！")
    private Boolean fixEnum;

    /**
     * 后端枚举名
     */
    private String enumBack;

    /**
     * 前端枚举名
     */
    private String enumFront;

    /**
     * 正则表达式
     */
    private String regularExpression;

    /**
     * 是否排序字段
     */
    @NotNull(message = "是否排序字段不能为空！")
    private Boolean isOrder;

    /**
     * 排序类型
     */
    @IsEnum(message = "排序类型格式不正确！", enumClass = GenOrderType.class)
    private String orderType;
}
