package com.lframework.starter.web.components.generator;


import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.utils.ObjectUtil;
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

    public static final Map<Class<? extends GenerateCodeType>, Generator> GENERATOR_POOL = new ConcurrentHashMap<>();

    public static Generator getInstance(GenerateCodeType type) {

        Generator generator = GENERATOR_POOL.get(type.getClass());
        if (generator == null) {
            synchronized (GenerateCodeFactory.class) {
                generator = GENERATOR_POOL.get(type.getClass());
                if (generator == null) {

                    generator = getGenrator(type);
                    if (generator == null) {
                        //如果未找到generator就使用默认generator
                        generator = getGenrator(GenerateCodeType.DEFAULT);
                    }

                    if (generator == null) {
                        throw new DefaultSysException("未找到" + type + "单号生成器！");
                    }

                    GENERATOR_POOL.put(type.getClass(), generator);
                }
            }
        }

        return generator;
    }

    private static Generator getGenrator(GenerateCodeType type) {

        Map<String, Generator> generators = ApplicationUtil.getBeansOfType(Generator.class);
        for (Generator value : generators.values()) {
            if (!value.isSpecial()) {
                // 优先匹配自定义生成器
                if (value.getType().getClass() == type.getClass()) {
                    return value;
                }
            }
        }

        for (Generator value : generators.values()) {
            if (value.isSpecial()) {
                // 匹配内置生成器
                if (value.getType().getClass() == type.getClass()) {
                    return value;
                }
            }
        }

        return null;
    }
}
