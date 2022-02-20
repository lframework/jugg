package com.lframework.gen.vo.dataobj;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdateUpdateColumnConfigVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "ID不能为空！")
    private String id;

    /**
     * 是否必填
     */
    @NotNull(message = "是否必填不能为空！")
    private Boolean required;
}
