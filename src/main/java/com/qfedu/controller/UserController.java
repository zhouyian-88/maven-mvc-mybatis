package com.qfedu.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qfedu.mini.mvc.annotation.Controller;
import com.qfedu.mini.mvc.annotation.RequestMapping;
import com.qfedu.pojo.User;
import com.qfedu.service.UserService;
import com.qfedu.service.impl.UserServiceImpl;
import com.qfedu.util.DataResult;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @athor:zhouhaohui
 * @email:2873642764@qq.com
 * @desc:
 * @datetime:2022-11-30-22:24
 */
@Controller
public class UserController {

    @RequestMapping("/LoginServlet")
    public void toLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
    @RequestMapping("/UpdateUserServlet")
    public void updateUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    @RequestMapping("/UserBulkRemoveServlet")
    public void bulkDeleteUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    @RequestMapping("/UserMessageSaveServlet")
    public void insertUser(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        try {
            DiskFileItemFactory factory=new DiskFileItemFactory();
            ServletFileUpload upload=new ServletFileUpload(factory);
            List<FileItem> fileItems = upload.parseRequest(request);
            String newName="";
            Map<String,Object> userMap=new HashMap<>();
            for (FileItem fileItem : fileItems) {
                if (!fileItem.isFormField()) {//是不是普通表格
                    // 上传的文件
                    InputStream is = fileItem.getInputStream();//文件上传流
                    // 需要将文件重命名
                    newName= UUID.randomUUID().toString().replaceAll("-","");
                    // 获取到源文件中的名字取后缀
                    String realName = fileItem.getName();
                    newName= newName+realName.substring(realName.lastIndexOf("."));
                    File file=new File("D:/image",newName);
                    OutputStream out=new FileOutputStream(file);
                    IOUtils.copy(is,out);
                    out.close();
                    userMap.put(fileItem.getFieldName(),newName);
                }else{
                    String value = fileItem.getString();
                    value=new String(value.getBytes("ISO-8859-1"),"UTF-8");
                    userMap.put(fileItem.getFieldName(),value);
                }
            }
            userMap.put("userPwd","123456");
            // 完成一个保存用户信息的业务
            UserService userService=new UserServiceImpl();
            System.out.println("usermap"+userMap);
            boolean b = userService.addUser(userMap);

        } catch (FileUploadException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/UserMessageServlet")
    public void pageHelp(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    @RequestMapping("/LoadImageServlet")
    public void headImage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("image/jpeg");
        String fn = request.getParameter("fn");
        File file=new File("D:/image",fn);
        InputStream is=new FileInputStream(file);
        IOUtils.copy(is,response.getOutputStream());
    }
}

