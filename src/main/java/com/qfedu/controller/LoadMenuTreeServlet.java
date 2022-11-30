package com.qfedu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfedu.pojo.Menu;
import com.qfedu.service.MenuService;
import com.qfedu.service.impl.MenuServiceImpl;
import com.qfedu.util.HomeInfo;
import com.qfedu.util.LogoInfo;
import com.qfedu.util.MenuHome;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/LoadMenuTreeServlet")
public class LoadMenuTreeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");

        // 调用业务层获取Menu树结构 menuInfo
        MenuService menuService=new MenuServiceImpl();
       List<Menu> menuInfo = menuService.getMenuTreeInfo();
        // 装载MenuHome对象
        MenuHome menuHome=new MenuHome();
        menuHome.setHomeInfo(new HomeInfo());
        menuHome.setLogoInfo(new LogoInfo());
        menuHome.setMenuInfo(menuInfo);
        // 将menuHome对象转化为json，响应到客户端
        ObjectMapper om=new ObjectMapper();
        String menuJson = om.writeValueAsString(menuHome);
        response.getWriter().write(menuJson);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}