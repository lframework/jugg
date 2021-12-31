package com.lframework.starter.web.utils;

import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.utils.ArrayUtil;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.common.utils.ObjectUtil;
import com.lframework.starter.web.enums.BaseEnum;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 枚举工具类
 *
 * @author zmj
 */
@Slf4j
public class EnumUtil {

    private static final Map<Class<? extends BaseEnum<? extends Serializable>>, List<? extends BaseEnum<? extends Serializable>>> ENUM_POOL;

    static {
        ENUM_POOL = new ConcurrentHashMap<>();
    }

    public static <C extends BaseEnum<? extends Serializable>> String getDesc(Class<C> clazz, Serializable code) {

        List<C> enumList = getEnumList(clazz);

        for (C c : enumList) {
            if (c.getCode().equals(code)) {
                return c.getDesc();
            }
        }

        return null;
    }

    public static <C extends BaseEnum<? extends Serializable>> C getByCode(Class<C> clazz, Serializable code) {

        if (ObjectUtil.isNull(code)) {
            return null;
        }

        List<C> enumList = getEnumList(clazz);
        for (C c : enumList) {
            if (c.getCode().equals(code)) {
                return c;
            }
        }

        return null;
    }

    private static <C extends BaseEnum<? extends Serializable>> List<C> getEnumList(Class<C> clazz) {

        if (!ENUM_POOL.containsKey(clazz)) {
            try {
                List<C> enumList = new ArrayList<>();
                if (Enum.class.isAssignableFrom(clazz)) {
                    // 如果是Enum类型
                    Method method = clazz.getMethod("values");

                    C[] enums = (C[]) method.invoke(clazz);

                    if (ArrayUtil.isNotEmpty(enums)) {
                        enumList.addAll(Arrays.asList(enums));
                    }
                } else {
                    // 如果不是Enum类型，那么必须是Bean
                    Map<String, C> enums = ApplicationUtil.getBeansOfType(clazz);
                    if (!CollectionUtil.isEmpty(enums)) {
                        enumList.addAll(enums.values());
                    }
                }
                ENUM_POOL.put(clazz, enumList);
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                log.error(e.getMessage(), e);
                throw new DefaultSysException(e.getMessage());
            }
        }

        List<C> enumList = (List<C>) ENUM_POOL.get(clazz);

        return enumList;
    }
}
