package com.lframework.gen.vo.simpledb;

import com.lframework.gen.enums.GenConvertType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author: zg
 * @create: 2021/06/02 15:38
 * @description:
 */
@Data
public class CreateSimpleTableVo implements BaseVo, Serializable {


    private static final long serialVersionUID = -1408536300284091591L;

    /**
     * 数据对象ID
     */
    private String dataObjId;

    /**
     * 转换方式
     */
    @NotNull(message = "请选择转换方式！")
    @IsEnum(message = "请选择转换方式！", enumClass = GenConvertType.class)
    private Integer convertType;

    /**
     * 数据表所属的数据库名
     */
    @NotBlank(message = "请输入数据库名！")
    private String tableSchema;

    /**
     * 数据库表名
     */
    @NotBlank(message = "请输入数据库表名！")
    private String tableName;
}
