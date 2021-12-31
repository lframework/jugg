package com.lframework.starter.web.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeType;
import com.lframework.common.exceptions.impl.DefaultSysException;
import com.lframework.common.utils.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = ApplicationUtil.getBean(ObjectMapper.class);

    public static String toJsonString(Object obj) {

        if (obj == null) {
            return null;
        }
        try {
            return OBJECT_MAPPER.writer().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new DefaultSysException(e.getMessage());
        }
    }

    public static <T> T parseObject(String jsonStr, Class<T> clazz) {

        if (StringUtil.isEmpty(jsonStr) || clazz == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(jsonStr, clazz);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new DefaultSysException(e.getMessage());
        }
    }

    public static <T> List<T> parseList(String jsonStr, Class<T> clazz) {

        if (StringUtil.isEmpty(jsonStr) || clazz == null) {
            return null;
        }

        try {
            return OBJECT_MAPPER.readValue(jsonStr,
                    OBJECT_MAPPER.getTypeFactory().constructCollectionType(List.class, String.class));
        } catch (JsonProcessingException e) {
            log.error(e.getMessage(), e);
            throw new DefaultSysException(e.getMessage());
        }
    }

    public static <T> T convert(Object obj, Class<T> clazz) {

        if (obj == null || clazz == null) {
            return null;
        }

        return OBJECT_MAPPER.convertValue(obj, clazz);
    }

    public static boolean isJsonObject(String jsonStr) {

        if (StringUtil.isBlank(jsonStr)) {
            return false;
        }

        try {
            JsonNode jsonNode = OBJECT_MAPPER.readTree(jsonStr);
            return jsonNode.getNodeType() != JsonNodeType.ARRAY;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    public static boolean isJsonArray(String jsonStr) {

        if (StringUtil.isBlank(jsonStr)) {
            return false;
        }

        try {
            JsonNode jsonNode = OBJECT_MAPPER.readTree(jsonStr);
            return jsonNode.getNodeType() == JsonNodeType.ARRAY;
        } catch (JsonProcessingException e) {
            return false;
        }
    }

    public static boolean isJson(String jsonStr) {
        if (StringUtil.isBlank(jsonStr)) {
            return false;
        }

        try {
            OBJECT_MAPPER.readTree(jsonStr);
        } catch (JsonProcessingException e) {
            return false;
        }

        return true;
    }
}
