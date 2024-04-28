package com.lib.common.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * Common json converter
 * */
@Slf4j
public class JsonMapper {

    /**
     * Object to Bytes
     * */
    public static byte[] toJsonBytes(Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            log.info("Cannot map object to bytes: {}", obj.getClass().getSimpleName());
            return null;
        }
    }

    /**
     * Bytes to Object
     * */
    public static <T> T toObject(byte[] bytes, TypeReference<T> type) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(bytes, type);
        } catch (IOException e) {
            log.info("Cannot map bytes[] to {}", type.getClass().getSimpleName());
            return null;
        }
    }

    /**
     * Object to Json String
     * */
    public static String toJsonString(Object obj) {
        byte[] bytes = toJsonBytes(obj);
        return bytes == null ? null : new String(bytes);
    }

    /**
     * Json string to Object
     * */
    public static <T> T toObject(String json, TypeReference<T> type) {
        return toObject(json.getBytes(), type);
    }
}
