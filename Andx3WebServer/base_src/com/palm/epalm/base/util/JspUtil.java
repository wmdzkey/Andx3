package com.palm.epalm.base.util;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.*;
import java.nio.charset.Charset;

/**
 * -将Jsp文件生成静态文件的工具类
 *
 * @author desire
 * @since 2013-04-07 11:03
 */
public class JspUtil {
    public static void jsp2txt_file(HttpServletRequest request, HttpServletResponse response, String jsp, String out) throws ServletException, IOException {
        String url = "";
        String name = "";
        Charset charset = Charset.forName("UTF-8");  //设置生成文件的字符集
        ServletContext servletContext = request.getSession().getServletContext();
        // String file_name = request.getParameter("file_name");
        url = "/" + jsp;//"/" + file_name + ".jsp";    // 你要生成的页面动态页面来源
        name = servletContext.getRealPath("/") + out; //"\\" + file_name + ".html";
        // 这是生成的html文件名,如index.html文件名字与源文件名相同

        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(url);


        final ByteArrayOutputStream outStream = new ByteArrayOutputStream();

        final ServletOutputStream stream = new ServletOutputStream() {
            public void write(byte[] data, int offset, int length) {
                outStream.write(data, offset, length);
            }

            public void write(int b) throws IOException {
                outStream.write(b);
            }
        };

        final PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(outStream, charset));

        HttpServletResponse rep = new HttpServletResponseWrapper(response) {
            public ServletOutputStream getOutputStream() {
                return stream;
            }

            public PrintWriter getWriter() {
                return printWriter;
            }
        };
        requestDispatcher.include(request, rep);
        printWriter.flush();
        FileOutputStream fos = new FileOutputStream(name); // 把jsp输出的内容写到html文件中去
        outStream.writeTo(fos);
        fos.close();
    }
}
