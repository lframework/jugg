package com.lframework.gen.vo.dataobj;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UpdateDetailColumnConfigVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull(message = "ID不能为空！")
    private String id;

    /**
     * 列宽
     */
    @NotNull(message = "列宽不能为空！")
    private Integer span;
}
