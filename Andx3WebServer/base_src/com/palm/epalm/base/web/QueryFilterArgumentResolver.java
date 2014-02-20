package com.palm.epalm.base.web;

import com.palm.epalm.base.repository.QueryFilter;
import com.palm.epalm.base.util.StringEncodeUtil;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebArgumentResolver;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author desire
 * @since 2013-04-26 12:04
 */
public class QueryFilterArgumentResolver implements WebArgumentResolver {
    @Override
    public Object resolveArgument(MethodParameter methodParameter, NativeWebRequest nativeWebRequest) throws Exception {
        Iterator<String> params = nativeWebRequest.getParameterNames();
        if (methodParameter.getParameterType().equals(QueryFilter.class)) {
            boolean isUTF_8 = "UTF-8".equals(((HttpServletRequest) nativeWebRequest.getNativeRequest()).getCharacterEncoding());
            while (params.hasNext()) {
                String param = params.next();
                if (param.startsWith("Q_")) {
                    String name = param.replaceFirst("Q_", "");
                    return isUTF_8 ? new QueryFilter(name, nativeWebRequest.getParameter(param))
                            : new QueryFilter(name, StringEncodeUtil.code_8859_1_to_UTF_8(nativeWebRequest.getParameter(param)));
                }
            }
            return UNRESOLVED;
        }
        if (methodParameter.getParameterType().isArray() && methodParameter.getParameterType().getComponentType().equals(QueryFilter.class)) {
            ArrayList<QueryFilter> queryFilters = new ArrayList<QueryFilter>();
            boolean isUTF_8 = "UTF-8".equals(((HttpServletRequest) nativeWebRequest.getNativeRequest()).getCharacterEncoding());
            while (params.hasNext()) {
                String param = params.next();
                if (param.startsWith("Q_")) {
                    String name = param.replaceFirst("Q_", "");
                    String value = nativeWebRequest.getParameter(param);
                    if ("".equals(value) || value == null) {
                        continue;
                    }
                    QueryFilter queryFilter = isUTF_8 ? new QueryFilter(name, value)
                            : new QueryFilter(name, StringEncodeUtil.code_8859_1_to_UTF_8(value));
                    queryFilters.add(queryFilter);
                }
            }
            return queryFilters.toArray(new QueryFilter[queryFilters.size()]);
        }
        return UNRESOLVED;
    }
}
