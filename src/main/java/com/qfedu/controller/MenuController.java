package com.qfedu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfedu.mini.mvc.annotation.Controller;
import com.qfedu.mini.mvc.annotation.RequestMapping;
import com.qfedu.pojo.Menu;
import com.qfedu.service.MenuService;
import com.qfedu.service.impl.MenuServiceImpl;
import com.qfedu.util.HomeInfo;
import com.qfedu.util.LogoInfo;
import com.qfedu.util.MenuHome;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @athor:zhouhaohui
 * @email:2873642764@qq.com
 * @desc:
 * @datetime:2022-11-30-22:25
 */
@Controller
public class MenuController {
    @RequestMapping("/LoadMenuTreeServlet")
    public void menuTree(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
}
