package com.lframework.gen.vo.gen;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import java.io.Serializable;

@Data
public class QueryParamsColumnConfigVo implements BaseVo, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    private String id;

    /**
     * 查询类别
     */
    private Integer queryType;
}
