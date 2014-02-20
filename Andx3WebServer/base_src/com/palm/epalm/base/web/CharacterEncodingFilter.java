package com.palm.epalm.base.web;

/**
 * Date: 13-3-19
 * Time: 下午2:47
 *  <init-param>
     <param-name>encoding</param-name>
     <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
     <param-name>forceEncoding</param-name>
     <param-value>true</param-value>
    </init-param>
 * @author desire
 * @version 1.0
 *
    @WebFilter(filterName = "encodingFilter",urlPatterns = "/*",initParams = {
        @WebInitParam(name = "encoding",value = "UTF-8"),
        @WebInitParam(name = "forceEncoding",value = "true")
    },asyncSupported = true)
 */
public class CharacterEncodingFilter extends org.springframework.web.filter.CharacterEncodingFilter {
    /**
    private String encoding;
    private boolean forceEncoding;
    public void setEncoding(String encoding) {
        super.setEncoding(encoding);
        this.encoding = encoding;
    }

    public void setForceEncoding(boolean forceEncoding) {
        super.setForceEncoding(forceEncoding);
        this.forceEncoding = forceEncoding;
    }
    protected void doFilterInternal(
            HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
      //  super.doFilter(request,response,filterChain);

        if (this.forceEncoding || request.getCharacterEncoding() == null) {
            request.setCharacterEncoding(this.encoding);
            response.setCharacterEncoding(this.encoding);
            response.setContentType("text/html; charset="+this.encoding);
        }
       // filterChain.doFilter(request, response);
        super.doFilterInternal(request,response,filterChain);
    }
    **/
}
