package com.lframework.gen.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

public enum DataObjectType implements BaseEnum<Integer> {
    SIMPLE_DB(1, "数据库单表");

    @EnumValue
    private final Integer code;

    private final String desc;

    DataObjectType(Integer code, String desc) {

        this.code = code;
        this.desc = desc;
    }

    @Override
    public Integer getCode() {

        return this.code;
    }

    @Override
    public String getDesc() {

        return this.desc;
    }
}
