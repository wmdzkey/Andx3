package com.palm.epalm.base.web;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.palm.epalm.modules.system.util.JsonUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import java.lang.reflect.Array;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created on 13-8-21 下午8:05.
 *
 * @author desire
 * @version 1.0
 */
public class CollectionArgumentResolver implements WebArgumentResolver {

    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest nativeWebRequest) throws Exception {
        //    methodParameter.getParameterType();
        Class type = methodParameter.getParameterType();//methodParameter.getGenericParameterType();
        String name = methodParameter.getParameterName();
        if (List.class.equals(type)) {
            ParameterizedType genType = (ParameterizedType) methodParameter.getGenericParameterType();
            Class etype = (Class) genType.getActualTypeArguments()[0];
            if (!isPrimitive(etype)) {
                //if(nativeWebRequest.getParameter("name"))
                String value = nativeWebRequest.getParameter(name);   //json值
                if (value != null && !"".equals(value)) {
                    JavaType javaType = TypeFactory.defaultInstance().constructCollectionType(ArrayList.class, etype);
                    return JsonUtil.toBean(value, javaType);
                }
                return UNRESOLVED;
            }
        } else if (type.isArray() && !isPrimitive(type.getComponentType())) {
            String value = nativeWebRequest.getParameter(name);    //json值
            if (value != null && !"".equals(value)) {
                return JsonUtil.toBean(value, type);
            }
            return UNRESOLVED;
        }
        return UNRESOLVED;
    }

    private List<?> getParamList(NativeWebRequest nativeWebRequest, Class elemType, String name) {
        Iterator<String> params = nativeWebRequest.getParameterNames();
        return null;
    }

    private boolean isPrimitive(Class type) {
        return type.isPrimitive() ? true : type.getPackage().getName().equals("java.lang") || type.getPackage().getName().equals("java.util");
    }
}
