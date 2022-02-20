package com.lframework.gen.vo.dataobj;

import com.lframework.gen.enums.GenKeyType;
import com.lframework.gen.enums.GenTemplateType;
import com.lframework.starter.web.components.validation.IsEnum;
import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdateGenerateInfoVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 生成模板类型
     */
    @NotNull(message = "生成模板类型不能为空！")
    @IsEnum(message = "生成模板类型格式不正确！", enumClass = GenTemplateType.class)
    private Integer templateType;

    /**
     * 包名
     */
    @NotBlank(message = "包名不能为空！")
    private String packageName;

    /**
     * 模块名称
     */
    @NotBlank(message = "模块名称不能为空！")
    private String moduleName;

    /**
     * 业务名称
     */
    @NotBlank(message = "业务名称不能为空！")
    private String bizName;

    /**
     * 类名
     */
    @NotBlank(message = "类名不能为空！")
    private String className;

    /**
     * 类描述
     */
    @NotBlank(message = "类描述不能为空！")
    private String classDescription;

    /**
     * 父级菜单ID
     */
    private String parentMenuId;

    /**
     * 主键类型
     */
    @NotNull(message = "主键类型不能为空！")
    @IsEnum(message = "主键类型格式不正确！", enumClass = GenKeyType.class)
    private Integer keyType;

    /**
     * 作者
     */
    private String author;

    /**
     * 本级菜单编号
     */
    @NotNull(message = "本级菜单编号不能为空！")
    private String menuCode;

    /**
     * 本级菜单名称
     */
    @NotNull(message = "本级菜单名称不能为空！")
    private String menuName;

    /**
     * 详情页Span总数量
     */
    @NotNull(message = "详情页Span总数量不能为空！")
    @Min(value = 1, message = "详情页Span总数量必须大于0！")
    private Integer detailSpan;

    /**
     * 是否应用缓存
     */
    @NotNull(message = "是否应用缓存不能为空！")
    private Boolean isCache;

    /**
     * 是否内置删除功能
     */
    @NotNull(message = "是否内置删除功能不能为空！")
    private Boolean hasDelete;
}
