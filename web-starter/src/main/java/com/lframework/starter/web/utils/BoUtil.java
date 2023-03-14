package com.lframework.starter.web.utils;

import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.EnumUtil;
import com.lframework.starter.common.functions.SFunction;
import com.lframework.starter.common.utils.ArrayUtil;
import com.lframework.starter.common.utils.BeanUtil;
import com.lframework.starter.common.utils.ReflectUtil;
import com.lframework.starter.web.annotations.convert.EncryptConvert;
import com.lframework.starter.web.annotations.convert.EnumConvert;
import com.lframework.starter.web.annotations.convert.IgnoreConvert;
import com.lframework.starter.web.annotations.constants.EncryType;
import com.lframework.starter.web.bo.BaseBo;
import com.lframework.starter.web.dto.BaseDto;
import com.lframework.starter.web.enums.BaseEnum;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoUtil {

  private static final Map<Class<? extends BaseBo>, CopyOptions> OPTIONS;

  static {
    OPTIONS = new HashMap<>();
  }

  public static <T extends BaseDto, B extends BaseBo, A> B convert(T source, B target,
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

  private static <A, B extends BaseBo, T extends BaseDto> CopyOptions buildCopyOptions(T source,
      B target,
      SFunction<A, ?>... columns) {
    if (OPTIONS.containsKey(target.getClass())) {
      return OPTIONS.get(target.getClass());
    }

    synchronized (BoUtil.class) {
      if (OPTIONS.containsKey(target.getClass())) {
        return OPTIONS.get(target.getClass());
      }

      List<String> columnNames = new ArrayList<>();
      if (!ArrayUtil.isEmpty(columns)) {
        for (int i = 0; i < columns.length; i++) {
          columnNames.add(ReflectUtil.getFieldName(columns[i]));
        }
      }

      Map<String, Object[]> enumFieldNames = new HashMap<>();
      Map<String, EncryType> encryptFieldNames = new HashMap<>();
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
        }
      }

      CopyOptions copyOptions = CopyOptions.create()
          .setIgnoreProperties(ArrayUtil.toArray(columnNames, String.class))
          .setFieldValueEditor((s, v) -> {

            if (enumFieldNames.containsKey(s)) {
              if (v != null) {
                return enumFieldNames.get(s)[(Integer) v];
              }
            }

            if (encryptFieldNames.containsKey(s)) {
              if (v instanceof CharSequence) {
                return FieldEncryptUtil.encrypt((CharSequence) v, encryptFieldNames.get(s));
              }
            }

            return v;
          });

      OPTIONS.put(target.getClass(), copyOptions);

      return copyOptions;
    }
  }
}
