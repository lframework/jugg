package com.lframework.starter.web.components.generator.impl;

import com.lframework.common.constants.StringPool;
import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.utils.DateUtil;
import com.lframework.starter.redis.components.RedisHandler;
import com.lframework.starter.web.components.code.GenerateCodeType;
import com.lframework.starter.web.components.generator.Generator;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

/**
 * 流水号生成器
 *
 * @author zmj
 */
public abstract class AbstractFlowGenerator extends AbstractGenerator implements Generator {

    private static final String LOCK_KEY = "flow_generator_index";

    @Autowired
    private RedisHandler redisHandler;

    @Override
    public String generate() {

        GenerateCodeType type = getType();
        if (type == null) {
            throw new DefaultSysException("code为null！");
        }
        String lockerName = LOCK_KEY + type.getClass().getName();
        String nowStr = DateUtil.formatDate(LocalDate.now(), "yyyyMMdd");
        String redisKey = nowStr + lockerName;
        long no = redisHandler.incr(redisKey, 1L);
        redisHandler.expire(redisKey, 86400000L);

        String noStr = String.valueOf(no);
        if (noStr.length() > getCodeLength()) {
            throw new DefaultSysException("单号超长！");
        }

        StringBuilder builder = new StringBuilder();
        builder.append(getPreffix()).append(nowStr);
        for (int i = 0, len = getCodeLength() - noStr.length(); i < len; i++) {
            builder.append("0");
        }

        return builder.append(noStr).toString();
    }

    /**
     * 获取流水号长度
     * @return
     */
    protected int getCodeLength() {

        return 10;
    }

    protected String getPreffix() {

        return "";
    }
}
