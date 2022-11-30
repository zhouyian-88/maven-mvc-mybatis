package com.qfedu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfedu.pojo.User;
import com.qfedu.service.UserService;
import com.qfedu.service.impl.UserServiceImpl;
import com.qfedu.util.DataResult;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet("/UserMessageServlet")
public class UserMessageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        String userName = request.getParameter("userName");
        String userSex = request.getParameter("userSex");
        String userAddress = request.getParameter("userAddress");

        User user=new User();
        user.setUserName(userName);
        user.setUserSex(userSex);
        user.setUserAddress(userAddress);

        // 请求参数 获取到当前第几页，每页显示多少条数据
        int pageNumber=Integer.parseInt(request.getParameter("page"));
        int pageSize=Integer.parseInt(request.getParameter("limit"));

        UserService userService=new UserServiceImpl();
        // 调用业务逻辑方法获取到数据对象
        DataResult result=userService.loadUserMessage(pageNumber,pageSize,user);
        // 结果需要转化为json
        ObjectMapper om=new ObjectMapper();
        String jsonResult = om.writeValueAsString(result);
        // 最终需要响应一个结果数据，这个数据是一个json
        response.getWriter().write(jsonResult);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}