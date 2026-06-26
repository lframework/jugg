package com.lframework.starter.web.core.utils;

import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.EnumUtil;
import com.lframework.starter.common.functions.SFunction;
import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.BeanUtil;
import com.lframework.starter.common.utils.ReflectUtil;
import com.lframework.starter.web.core.annotations.constants.EncryType;
import com.lframework.starter.web.core.annotations.convert.DecryptConvert;
import com.lframework.starter.web.core.annotations.convert.EncryptConvert;
import com.lframework.starter.web.core.annotations.convert.EnumConvert;
import com.lframework.starter.web.core.annotations.convert.IgnoreConvert;
import com.lframework.starter.web.core.bo.BaseBo;
import com.lframework.starter.web.core.dto.BaseDto;
import com.lframework.starter.web.core.enums.BaseEnum;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 业务对象转换工具类
 * 提供DTO到BO的转换功能，支持字段忽略、枚举转换、加密转换等
 * 包括对象属性复制、字段转换、注解处理等功能
 *
 * @author lframework@163.com
 */
public class BoUtil {

  private static final Map<Class<? extends BaseBo<?>>, CopyOptions> OPTIONS;

  static {
    OPTIONS = new HashMap<>();
  }

  @SuppressWarnings({"unchecked", "varargs"})
  public static <T extends BaseDto, B extends BaseBo<?>, A> B convert(T source, B target,
      SFunction<A, ?>... columns) {

    if (source == null) {
      return null;
    }

    if (target == null) {
      return null;
    }

    BeanUtil.copyProperties(source, target, buildCopyOptions(source, target, columns));

    return target;
  }

  @SuppressWarnings({"unchecked", "varargs"})
  private static <A, B extends BaseBo<?>, T extends BaseDto> CopyOptions buildCopyOptions(T source,
      B target,
      SFunction<A, ?>... columns) {
    Class<? extends BaseBo<?>> targetClass = (Class<? extends BaseBo<?>>) target.getClass();
    if (OPTIONS.containsKey(targetClass)) {
      return OPTIONS.get(targetClass);
    }

    synchronized (BoUtil.class) {
      if (OPTIONS.containsKey(targetClass)) {
        return OPTIONS.get(targetClass);
      }

      List<String> columnNames = new ArrayList<>();
      if (!ArrayUtil.isEmpty(columns)) {
        for (int i = 0; i < columns.length; i++) {
          columnNames.add(ReflectUtil.getFieldName(columns[i]));
        }
      }

      Map<String, Object[]> enumFieldNames = new HashMap<>();
      Map<String, List<String>> enumNameMap = new HashMap<>();
      Map<String, EncryType> encryptFieldNames = new HashMap<>();
      List<String> decryptFieldNames = new ArrayList<>();
      Class<? extends Serializable> clazz = target.getClass();
      Field[] fields = ReflectUtil.getFields(clazz);
      if (ArrayUtil.isNotEmpty(fields)) {
        for (int i = 0; i < fields.length; i++) {
          Field field = fields[i];
          IgnoreConvert ignore = field.getAnnotation(IgnoreConvert.class);
          if (ignore != null) {
            columnNames.add(field.getName());
            // 如果忽略了，后面的检查就不需要了
            continue;
          }

          EnumConvert enumConvert = field.getAnnotation(EnumConvert.class);
          if (enumConvert != null) {
            String fieldName = field.getName();
            Field srcField = ReflectUtil.getField(source.getClass(), fieldName);
            if (srcField != null) {
              if (ClassUtil.isAssignable(Enum.class, srcField.getType()) && ClassUtil
                  .isAssignable(BaseEnum.class, srcField.getType())) {
                List<Object> codes = EnumUtil
                    .getFieldValues((Class<? extends Enum<?>>) srcField.getType(), "code");

                List<String> enumNames = EnumUtil.getNames((Class<? extends Enum<?>>) srcField.getType());
                enumNameMap.put(field.getName(), enumNames);

                enumFieldNames.put(field.getName(), ArrayUtil.toArray(codes, Object.class));
              }
            }
          }
          EncryptConvert encryptConvert = field.getAnnotation(EncryptConvert.class);
          if (encryptConvert != null) {
            EncryType encryType = encryptConvert.type();
            String fieldName = field.getName();
            encryptFieldNames.put(fieldName, encryType);
          }
          DecryptConvert decryptConvert = field.getAnnotation(DecryptConvert.class);
          if (decryptConvert != null) {
            decryptFieldNames.add(field.getName());
          }
        }
      }

      CopyOptions copyOptions = CopyOptions.create()
          .setIgnoreProperties(ArrayUtil.toArray(columnNames, String.class))
          .setFieldValueEditor((s, v) -> {

            if (enumFieldNames.containsKey(s)) {
              if (v != null) {
                if (v instanceof CharSequence) {
                  // 枚举值是字符串时
                  return enumFieldNames.get(s)[enumNameMap.get(s).indexOf(v)];
                } else {
                  // 这里只能当做是数字，enumConvert只支持两种形式
                  return enumFieldNames.get(s)[Integer.parseInt(String.valueOf(v))];
                }
              }
            }

            if (encryptFieldNames.containsKey(s)) {
              if (v instanceof CharSequence) {
                return FieldEncryptUtil.encrypt((CharSequence) v, encryptFieldNames.get(s));
              }
            }

            if (decryptFieldNames.contains(s)) {
              if (v instanceof CharSequence) {
                return decrypt((CharSequence) v);
              }
            }

            return v;
          });

      OPTIONS.put(targetClass, copyOptions);

      return copyOptions;
    }
  }

  private static String decrypt(CharSequence value) {

    try {
      return EncryptUtil.decrypt(value.toString());
    } catch (RuntimeException e) {
      // 兼容历史明文数据，避免老数据无法转换为BO。
      return value.toString();
    }
  }
}
