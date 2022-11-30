package com.qfedu.controller;

import com.qfedu.service.UserService;
import com.qfedu.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/UserBulkRemoveServlet")
public class UserBulkRemoveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        // 获取请求参数
        Map<String, String[]> paramMap = request.getParameterMap();
        String[] userIds = paramMap.get("userId");//获取所有被删除的用户ID
       // 执行删除操作的业务
        UserService userService =new UserServiceImpl();
        boolean removeOK=userService.bachRemoveUser(userIds);
        if (removeOK) {
            response.getWriter().write("{\"code\":200}");
        }else{
            response.getWriter().write("{\"code\":300}");
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}