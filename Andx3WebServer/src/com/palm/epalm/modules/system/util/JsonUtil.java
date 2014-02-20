package com.palm.epalm.modules.system.util;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.io.StringWriter;
import java.lang.reflect.Type;

/**
 * Created on 13-8-21 下午7:08.
 *
 * @author desire
 * @version 1.0
 */
public class JsonUtil {
    private static ObjectMapper mapper = new ObjectMapper();

    public static String toJson(Object obj) {
        // 这里异常都未进行处理，而且流的关闭也不规范。开发中请勿这样写，如果发生异常流关闭不了
        // ObjectMapper mapper = CommonUtil.getMapperInstance(false);
        StringWriter writer = new StringWriter();
        JsonGenerator gen = null;
        try {
            gen = new JsonFactory().createJsonGenerator(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            mapper.writeValue(gen, obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            gen.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String json = writer.toString();
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static <T> T toBean(String src, Class<T> type) {
        try {
            return mapper.readValue(src, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <T> T toBean(String src, JavaType type) {
        try {
            return mapper.readValue(src, type);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
