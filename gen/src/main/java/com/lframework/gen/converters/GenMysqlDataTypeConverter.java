package com.lframework.gen.converters;

import com.lframework.gen.enums.GenDataType;
import com.lframework.gen.enums.GenMySqlDataType;
import org.springframework.stereotype.Component;

@Component
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
