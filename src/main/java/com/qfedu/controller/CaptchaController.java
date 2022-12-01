package com.qfedu.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.qfedu.mini.mvc.annotation.Controller;
import com.qfedu.mini.mvc.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @athor:zhouhaohui
 * @email:2873642764@qq.com
 * @desc:
 * @datetime:2022-11-30-22:25
 */
@Controller
public class CaptchaController {
    @RequestMapping("/CaptchaServlet")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        LineCaptcha captcha= CaptchaUtil.createLineCaptcha(100,38,4,20);

        // 获取到生成的验证码
        String code = captcha.getCode();
        // 将验证码放到session中
        HttpSession session = request.getSession();
        session.setAttribute("captchaCode",code);
        System.out.println(code);
        captcha.write(response.getOutputStream());

    }
}
