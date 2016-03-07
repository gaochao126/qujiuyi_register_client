package com.jiuyi.qujiuyi.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * @description BaseServlet
 * @author zhb
 * @createTime 2015年12月26日
 */
public class BaseServlet extends HttpServlet {
    /** serialVersionUID. */
    private static final long serialVersionUID = 3543988573724847766L;

    protected final static Logger logger = Logger.getLogger(BaseServlet.class);

    /**
     * @description 响应服务端
     * @param response
     * @param content
     */
    protected static void print(HttpServletResponse response, String content) {
        PrintWriter out = null;
        try {
            out = response.getWriter();
            out.print(content);
        } catch (IOException e) {
            logger.error("", e);
        } finally {
            if (out != null) {
                out.flush();
                out.close();
            }
        }
    }

    /**
     * @description 获取请求上报数据
     * @param request
     * @return
     */
    protected static String getRequestContent(HttpServletRequest request) {
        String content = "";
        try {
            InputStream in = request.getInputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = in.read(buffer)) != -1) {
                content += new String(buffer, 0, len, "UTF-8");
            }
            in.close();
        } catch (IOException e) {
            logger.error("", e);
        }
        return content;
    }
}