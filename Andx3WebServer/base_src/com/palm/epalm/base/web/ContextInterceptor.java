package com.palm.epalm.base.web;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.sitemesh.webapp.contentfilter.HttpServletResponseBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CacheAnnotationParser;
import org.springframework.cache.annotation.SpringCacheAnnotationParser;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.*;
import org.springframework.core.MethodParameter;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.nio.CharBuffer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author desire
 * @since 2013-07-30 19:43
 */
public class ContextInterceptor implements HandlerInterceptor {
    protected final Log logger = LogFactory.getLog(getClass());


    //RequestMappingHandlerAdapter mappingHandlerAdapter;

    @Autowired
    EhCacheCacheManager cacheManager;
    CacheAnnotationParser cacheAnnotationParser = new SpringCacheAnnotationParser();
    private static final String CACHEABLE = "cacheable", UPDATE = "cacheupdate", EVICT = "cacheevict";
    Map<Object, CacheOperation> updateMap = new HashMap<Object, CacheOperation>();


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        boolean flag = true;
        if (o instanceof HandlerMethod && response instanceof HttpServletResponseWrapper) {

            Collection<CacheOperation> cacheOperations = cacheAnnotationParser.parseCacheAnnotations(((HandlerMethod) o).getMethod());
            if (!CollectionUtils.isEmpty(cacheOperations)) {
                boolean log = logger.isTraceEnabled();
                for (CacheOperation cacheOperation : cacheOperations) {
                    boolean isConditionPassing = true;//处理条件

                    if (cacheOperation instanceof CacheableOperation) {
                        //add
                        Object key = generateKey(cacheOperation, request, response, (HandlerMethod) o);
                        if (log) {
                            logger.trace("Computed cache key " + key + " for operation " + cacheOperation);
                        }
                        if (key == null) {
                            throw new IllegalArgumentException(
                                    "Null key returned for cache operation (maybe you are using named params on classes without debug info?) "
                                            + cacheOperation);
                        }
                        for (Cache cache : getCaches(cacheOperation)) {
                            Cache.ValueWrapper valueWrapper = cache.get(key);
                            if (valueWrapper != null) {
                                Object value = valueWrapper.get();
                                response.getWriter().print(value);
                                response.getWriter().flush();
                                flag = false;
                            }
                        }
                        updateMap.put(key, cacheOperation);
                    } else if (cacheOperation instanceof CacheEvictOperation) {
                        for (Cache cache : getCaches(cacheOperation)) {
                            cache.clear();
                        }
                    } else if (cacheOperation instanceof CachePutOperation) {
                        Object key = "default";//generateKey(cacheOperation,request,response,o);
                        if (log) {
                            logger.trace("Computed cache key " + key + " for operation " + cacheOperation);
                        }
                        updateMap.put(key, cacheOperation);
                    }
                }
            }
        }

        return flag;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object o, ModelAndView modelAndView) throws Exception {
        //do nothing
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object o, Exception e) throws Exception {
        if (!updateMap.isEmpty() && e == null) {
            String content = null;
            if (response instanceof HttpServletResponseBuffer) {//通过sitemesh的缓存取结果
                CharBuffer charBuffer = ((HttpServletResponseBuffer) response).getBuffer();
                content = charBuffer.toString();
            } else if (response instanceof HttpServletResponseWrapper) {//通过tomcat的缓存取结果
                ServletResponse resp = ((HttpServletResponseWrapper) response).getResponse();
                Object stream = resp.getOutputStream();
                Object ob = ReflectionTestUtils.getField(stream, "ob");
                Object outputChunk = ReflectionTestUtils.getField(ob, "outputChunk");
                // Object ob = ReflectionUtils.getField(stream.getClass().getField("ob"),stream);
                // Object outputChunk = ReflectionUtils.getField(ob.getClass().getField("outputChunk"),ob);
                content = outputChunk.toString();
            }

            //((org.apache.xalan.xsltc.runtime.output.OutputBuffer)(((HttpSessionSecurityContextRepository.SaveToSessionResponseWrapper) response).getResponse().getOutputStream().ob)).outputChunk
            if (content != null) {
                //缓存内容
                for (Object key : updateMap.keySet()) {
                    CacheOperation operation = updateMap.get(key);
                    for (Cache cache : getCaches(operation)) {
                        cache.put(key, content);
                    }
                }
            }

            updateMap.clear();
        }
    }

    protected Collection<Cache> getCaches(CacheOperation operation) {
        Set<String> cacheNames = operation.getCacheNames();
        Collection<Cache> caches = new ArrayList<Cache>(cacheNames.size());
        for (String cacheName : cacheNames) {
            Cache cache = this.cacheManager.getCache(cacheName);
            if (cache == null) {
                this.cacheManager.getCacheManager().addCache(cacheName);
                cache = cacheManager.getCache(cacheName);
                //throw new IllegalArgumentException("Cannot find cache named [" + cacheName + "] for " + operation);
            }
            caches.add(cache);
        }
        return caches;
    }

    Pattern pattern = Pattern.compile("#\\w+");

    protected Object generateKey(CacheOperation operation, HttpServletRequest request, HttpServletResponse response, HandlerMethod handlerMethod) {
        String key = operation.getKey();
        Map<String, String> paramNamesMap = getParamNamesMap(handlerMethod);
        String[] hints = handlerMethod.getMethodAnnotation(RequestMapping.class).value();
        String uri = request.getRequestURI();
        // List<String> paraNames = paramNamesMap
        Collection<String> paraNames = paramNamesMap.values();
        Map<String, String[]> paramMaps = request.getParameterMap();
        if (!StringUtils.isEmpty(key)) {
            if (key.indexOf("#") == -1) {
                return key;
            } else {
                Matcher matcher = pattern.matcher(key);
                while (matcher.find()) {
                    String p = matcher.group();
                    String p1 = p.replaceFirst("#", "");
                    String p_name = paramNamesMap.get(p1);
                    if (p_name.startsWith("@")) {
                        key.replaceAll(p, getPathValue(p_name, uri, hints));
                    } else {
                        key.replaceAll(p, request.getParameter(p_name));
                    }
                }
                return key;
            }
        }

        List<String> paramKey = new ArrayList<String>();
        for (String p : paraNames) {
            String name = p;
            if (name.startsWith("@")) {//路径参数
                name += getPathValue(name, uri, hints);
            } else {
                String[] values = paramMaps.get(name);
                if (values != null) {
                    for (String v : values) {
                        name += v;
                    }
                }
            }
            paramKey.add(name);
        }
        return generate(paramKey.toArray());
    }

    public static final int NO_PARAM_KEY = 0;
    public static final int NULL_PARAM_KEY = 53;

    public Object generate(Object[] params) {
        if (params.length == 1) {
            return (params[0] == null ? NULL_PARAM_KEY : params[0]);
        }
        if (params.length == 0) {
            return NO_PARAM_KEY;
        }
        int hashCode = 17;
        for (Object object : params) {
            hashCode = 31 * hashCode + (object == null ? NULL_PARAM_KEY : object.hashCode());
        }
        return Integer.valueOf(hashCode);
    }

    private String getPathValue(String name, String uri, String[] hints) {
        for (String hint : hints) {
            name = name.replaceFirst("@", "");
            String pattern = hint.replaceFirst("\\{" + name + "\\}", "(\\\\w+)").replaceAll("\\{\\w+\\}", "\\\\w+") + ".*$";
            String value = uri.replaceFirst(pattern, "$1");
            return value;
        }
        return "";
    }

    //Map<参数名，引用名>
    private Map<String, String> getParamNamesMap(HandlerMethod method) {
        Map<String, String> paramNames = new HashMap<String, String>();
        for (MethodParameter p : method.getMethodParameters()) {
            String name = p.getParameterName();
            PathVariable pv = p.getParameterAnnotation(PathVariable.class);
            RequestParam rp;
            String value;
            if (pv != null) {
                value = "@" + (StringUtils.isEmpty(pv.value()) ? name : pv.value());
            } else if ((rp = p.getParameterAnnotation(RequestParam.class)) != null) {
                value = StringUtils.isEmpty(rp.value()) ? name : rp.value();
            } else {
                value = name;
            }
            paramNames.put(name, value);
        }
        ;
        return paramNames;
    }
}
