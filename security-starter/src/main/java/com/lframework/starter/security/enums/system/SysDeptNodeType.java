package com.lframework.starter.security.enums.system;

import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public final class SysDeptNodeType implements NodeType, Serializable {

    private static final long serialVersionUID = 1L;

    @Override
    public Integer getCode() {

        return 1;
    }

    @Override
    public String getDesc() {

        return "部门";
    }
}
