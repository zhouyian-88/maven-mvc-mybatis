package com.qfedu.controller;

import com.qfedu.pojo.User;
import com.qfedu.service.UserService;
import com.qfedu.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/UpdateUserServlet")
public class UpdateUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String userId = request.getParameter("userId");
        String userName = request.getParameter("userName");
        String userPhone = request.getParameter("userPhone");
        String userAddress = request.getParameter("userAddress");
        User user=new User();
        user.setUserId(Integer.parseInt(userId));
        user.setUserName(userName);
        user.setUserPhone(userPhone);
        user.setUserAddress(userAddress);
        // 完成更新用户的业务
        UserService userService=new UserServiceImpl();
       boolean ok = userService.modifyUser(user);
        if (ok){
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