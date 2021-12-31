package com.lframework.starter.mybatis.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.lframework.starter.web.enums.BaseEnum;

/**
 * 操作日志类别
 *
 * @author zmj
 */
public enum OpLogType implements BaseEnum<Integer> {
    AUTH(1, "用户认证"), OTHER(2, "其他");

    @EnumValue
    private final Integer code;

    private final String desc;

    OpLogType(Integer code, String desc) {

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
