package com.qfedu.controller;

import com.qfedu.pojo.User;
import com.qfedu.service.UserService;
import com.qfedu.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        // 获取到请求参数
        String userName = request.getParameter("userName");
        String userPwd = request.getParameter("userPwd");
        String captcha = request.getParameter("captcha");
        String autoLogin = request.getParameter("autoLogin");

        HttpSession session = request.getSession();
        String captchaCode = (String)session.getAttribute("captchaCode");
        if (captcha.equals(captchaCode)){
            // 完成登录业务
            UserService userService=new UserServiceImpl();
            User user=userService.loginUser(userName,userPwd);
            if (user!=null){
                if ("true".equals(autoLogin)){
                    // 如果勾选了自动登录，象session中存储一个标志
                    session.setAttribute("autoLogin","auto");
                }
                session.setAttribute("user",user);
                response.getWriter().write("{\"code\":200,\"msg\":\"success\"}");
            }else{
                response.getWriter().write("{\"code\":300,\"msg\":\"用户名或者密码错误\"}");
            }

        }else{
            response.getWriter().write("{\"code\":300,\"msg\":\"验证码错误\"}");
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}