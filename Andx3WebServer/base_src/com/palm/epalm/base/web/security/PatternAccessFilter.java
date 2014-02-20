package com.palm.epalm.base.web.security;

import com.palm.epalm.base.context.ApplicationHelper;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.security.core.context.SecurityContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Date: 13-6-19
 * Time: 下午6:56
 *
 * @author desire
 * @version 1.0
 */
public class PatternAccessFilter implements AccessFilter {

    private String pattern;
    private String access;
    private List<String> varNames = new ArrayList<String>();
    private static final Pattern varNamePattern = Pattern.compile("\\{(\\w+)\\}");
    private Pattern varPattern;

    @Override
    public boolean doFilter(HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI().replace(request.getContextPath(), "");
        Matcher matcher = varPattern.matcher(uri);
        if (matcher.find()) {
            ExpressionParser exp = new SpelExpressionParser();
            StandardEvaluationContext context = new StandardEvaluationContext();
            //context.setVariable();
            Object principal = ApplicationHelper.getSecurityContext(request.getSession());
            if (principal != null) {
                principal = ((SecurityContext) principal).getAuthentication().getPrincipal();
            }
            context.setVariable("principal", principal);
            context.setVariable("request", request);
            for (int i = varNames.size(); i > 0; i--) {
                context.setVariable(varNames.get(i - 1), matcher.group(i));
            }
            boolean flag = exp.parseExpression(getAccess()).getValue(context, Boolean.class);
            if (flag) return true;
            else {
                try {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND);
                } catch (IOException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                return false;
            }
        } else {
            return true;
        }
    }

    public String getAccess() {
        return access;
    }

    public void setAccess(String access) {
        this.access = access;
    }

    public String getPattern() {

        return pattern;
    }

    public void setPattern(String pattern) {
        //  String p = pattern.replaceAll("\\{(\\w+)\\}","");
        //  Pattern p1=Pattern.compile("\\{(\\w+)\\}");
        //  p1.matcher(pattern);
        //  List<String> l;
        Matcher matcher = varNamePattern.matcher(pattern);
        while (matcher.find()) {
            varNames.add(matcher.group(1));
        }
        String p1 = "^" + (matcher.replaceAll("(\\\\w+)").replaceAll("\\*", ".*")) + "$";
        varPattern = Pattern.compile(p1);
        this.pattern = pattern;
    }

}
