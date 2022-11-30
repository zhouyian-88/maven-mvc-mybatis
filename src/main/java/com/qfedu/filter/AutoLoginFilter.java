package com.qfedu.filter;


import com.qfedu.pojo.User;
import com.qfedu.service.UserService;
import com.qfedu.service.impl.UserServiceImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


public class AutoLoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException { }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request= (HttpServletRequest) servletRequest;
        HttpServletResponse response= (HttpServletResponse) servletResponse;
        // 完成自动登录
        HttpSession session = request.getSession();
        String autoLogin = (String)session.getAttribute("autoLogin");
        User user=(User)session.getAttribute("user");
        if ("auto".equals(autoLogin)){
               // 调用登录业务逻辑
            UserService userService=new UserServiceImpl();
            User u=userService.loginUser(user.getUserName(),user.getUserPwd());
            if (u!=null){
                session.setAttribute("user",u);
                response.sendRedirect("page/index.html");
            }else{
                // 登录失败，放行，让它访问登录页手动登录
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
