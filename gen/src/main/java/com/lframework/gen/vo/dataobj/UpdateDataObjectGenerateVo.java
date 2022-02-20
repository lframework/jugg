package com.lframework.gen.vo.dataobj;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Data
public class UpdateDataObjectGenerateVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "ID不能为空！")
    private String id;

    /**
     * 字段信息
     */
    @Valid
    @NotEmpty(message = "字段信息不能为空！")
    private List<UpdateDataObjectColumnGenerateVo> columns;

    /**
     * 基础设置
     */
    @Valid
    @NotNull(message = "基本设置不能为空！")
    private UpdateGenerateInfoVo generateInfo;

    /**
     * 新增配置
     */
    @Valid
    private List<UpdateCreateColumnConfigVo> createConfigs;

    /**
     * 修改配置
     */
    @Valid
    private List<UpdateUpdateColumnConfigVo> updateConfigs;

    /**
     * 查询配置
     */
    @Valid
    private List<UpdateQueryColumnConfigVo> queryConfigs;

    /**
     * 查询参数配置
     */
    @Valid
    private List<UpdateQueryParamsColumnConfigVo> queryParamsConfigs;

    /**
     * 详情配置
     */
    @Valid
    private List<UpdateDetailColumnConfigVo> detailConfigs;
}
