package com.qfedu.controller;

import com.qfedu.service.UserService;
import com.qfedu.service.impl.UserServiceImpl;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@WebServlet("/UserMessageSaveServlet")
public class UserMessageSaveServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
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
}