package com.lframework.gen.converters;

import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.utils.CollectionUtil;
import com.lframework.gen.enums.GenDataType;
import com.lframework.gen.enums.GenViewType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class GenViewTypeConverter {

    public boolean canConvert(GenViewType viewType, GenDataType dataType) {

        List<GenViewType> viewTypes = convert(dataType);
        if (CollectionUtil.isEmpty(viewTypes)) {
            return false;
        }

        return viewTypes.contains(viewType);
    }

    public List<GenViewType> convert(GenDataType dataType) {

        if (dataType == null) {
            return null;
        }

        List<GenViewType> results = new ArrayList<>();
        if (dataType == GenDataType.STRING) {
            results.addAll(Arrays.asList(GenViewType.values()));
        } else if (dataType == GenDataType.INTEGER) {
            results.addAll(Arrays.asList(GenViewType.INPUT, GenViewType.SELECT));
        } else if (dataType == GenDataType.SHORT) {
            results.addAll(Arrays.asList(GenViewType.INPUT, GenViewType.SELECT));
        } else if (dataType == GenDataType.LONG) {
            results.addAll(Arrays.asList(GenViewType.INPUT));
        } else if (dataType == GenDataType.DOUBLE) {
            results.addAll(Arrays.asList(GenViewType.INPUT));
        } else if (dataType == GenDataType.LOCAL_DATE) {
            results.addAll(Arrays.asList(GenViewType.INPUT, GenViewType.DATE, GenViewType.DATE_RANGE));
        } else if (dataType == GenDataType.LOCAL_DATE_TIME) {
            results.addAll(Arrays.asList(GenViewType.INPUT, GenViewType.DATETIME, GenViewType.DATE_RANGE));
        } else if (dataType == GenDataType.LOCAL_TIME) {
            results.addAll(Arrays.asList(GenViewType.INPUT, GenViewType.TIME));
        } else if (dataType == GenDataType.BOOLEAN) {
            results.addAll(Arrays.asList(GenViewType.INPUT, GenViewType.SELECT));
        } else if (dataType == GenDataType.BIG_DECIMAL) {
            results.addAll(Arrays.asList(GenViewType.INPUT));
        } else {
            log.error("未知的GenDataType={}", dataType);
            throw new DefaultSysException("未知的GenDataType");
        }

        return results;
    }
}
