package com.lframework.gen.vo.gen;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateColumnConfigVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 是否必填
     */
    private Boolean required;
}
