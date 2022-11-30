package com.qfedu.service.impl;

import com.qfedu.mapper.UserMapper;
import com.qfedu.pojo.User;
import com.qfedu.service.UserService;
import com.qfedu.util.DataResult;
import com.qfedu.util.SqlSessionFactoryUtils;
import org.apache.ibatis.session.SqlSession;

import java.util.List;
import java.util.Map;

public class UserServiceImpl implements UserService {
    // 这个对象是用来操作user表的一个dao层对象
    private static final SqlSession sqlSession = SqlSessionFactoryUtils.GetSqlSession();
    private static final UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
    @Override
    public User loginUser(String userName, String userPwd) {
        User user=userMapper.findUserByUserName(userName);
        if (user!=null){
            // 判断密码是否正确
            if (userPwd.equals(user.getUserPwd())){
                return user;//登录成功
            }
        }
        return null;
    }

    @Override
    public DataResult loadUserMessage(int pageNumber, int pageSize,User user) {
        DataResult result=new DataResult();
        List<User> userList=userMapper.findUserByLimit((pageNumber-1)*pageSize,pageSize,user);
        // 查询当前数据库中有多少条记录
        long count=userMapper.getCount(user);
        result.setCount(count);
        result.setData(userList);
        return result;
    }

    @Override
    public boolean addUser(Map<String, Object> userMap) {
        int row=userMapper.insertUser(userMap);
        if (row>0){
            sqlSession.commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean bachRemoveUser(String[] userIds) {
        int row=userMapper.deleteBachUser(userIds);
        if (row==userIds.length){
            sqlSession.commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean modifyUser(User user) {
       int row= userMapper.updateUserById(user);
       if (row>0){
           sqlSession.commit();
           return true;
       }
        return false;
    }
}
