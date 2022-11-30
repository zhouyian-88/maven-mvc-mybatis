package com.qfedu.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CheckLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        // 1 获取到请求URI
        String uri = request.getRequestURI();
        // 2 获取到请求路径
        String path=uri.replaceAll(request.getContextPath(),"");
        // 获取session中的user
        Object user=request.getSession().getAttribute("user");
        // 判断User是否为空，空表示未登录
        if (user==null){
            if (path.endsWith(".html")||path.endsWith("Servlet")){
                if (path.equals("/login.html")
                        ||path.equals("/CaptchaServlet")
                        ||path.equals("/LoginServlet")){
                    filterChain.doFilter(request,response);
                }else{
                    response.sendRedirect(request.getContextPath()+"/login.html");
                }
            }else{
                filterChain.doFilter(request,response);
            }
        }else{
            filterChain.doFilter(request,response);
        }

    }

    @Override
    public void destroy() {

    }
}
