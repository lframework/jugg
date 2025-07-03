package com.lframework.starter.web.gen.converters;

import com.lframework.starter.web.gen.enums.GenDataType;
import com.lframework.starter.web.gen.enums.GenMySqlDataType;
import org.springframework.stereotype.Component;

public class GenMysqlDataTypeConverter {

  public GenMySqlDataType convert(GenDataType dataType) {

    GenMySqlDataType[] mySqlDataTypes = GenMySqlDataType.values();
    for (GenMySqlDataType mySqlDataType : mySqlDataTypes) {
      if (mySqlDataType.getDataType() == dataType) {
        return mySqlDataType;
      }
    }

    return null;
  }
}
