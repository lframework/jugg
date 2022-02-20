package com.lframework.gen.vo.simpledb;

import com.lframework.starter.web.vo.BaseVo;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetTablesVo implements BaseVo, Serializable {

    /**
     * 是否当前数据库
     */
    private Boolean isCurrentDb;

}
