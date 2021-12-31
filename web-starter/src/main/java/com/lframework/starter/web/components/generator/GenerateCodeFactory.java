package com.lframework.starter.web.components.generator;


import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.starter.web.components.code.GenerateCodeType;
import com.lframework.starter.web.utils.ApplicationUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 单号生成器Factory
 *
 * @author zmj
 */
public class GenerateCodeFactory {

    public static final Map<GenerateCodeType, Generator> GENERATOR_POOL = new ConcurrentHashMap<>();

    public static Generator getInstance(GenerateCodeType type) {

        Generator generator = GENERATOR_POOL.get(type);
        if (generator == null) {
            synchronized (GenerateCodeFactory.class) {
                if (generator == null) {

                    generator = getGenrator(type);
                    if (generator == null) {
                        //如果未找到generator就使用默认generator
                        generator = getGenrator(GenerateCodeType.DEFAULT);
                    }

                    if (generator == null) {
                        throw new DefaultSysException("未找到" + type + "单号生成器！");
                    }

                    GENERATOR_POOL.put(type, generator);
                }
            }
        }

        return generator;
    }

    private static Generator getGenrator(GenerateCodeType type) {

        Map<String, Generator> generators = ApplicationUtil.getBeansOfType(Generator.class);
        for (Generator value : generators.values()) {
            if (value.getType() == type) {
                return value;
            }
        }

        return null;
    }
}
