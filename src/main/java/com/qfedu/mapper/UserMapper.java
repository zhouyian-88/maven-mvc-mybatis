package com.qfedu.mapper;

import com.qfedu.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @athor:zhouhaohui
 * @email:2873642764@qq.com
 * @desc:
 * @datetime:2022-11-30-10:11
 */
@Mapper
public interface UserMapper {
    User findUserByUserName(String userName);

    List<User> findUserByLimit(@Param("pageNum") int pageNum,@Param("pageSize") int pageSize, @Param("user") User user);

    long getCount(@Param("user")User user);

    int insertUser(Map<String, Object> userMap);

    int deleteBachUser(String[] userIds);

    int updateUserById(@Param("user")User user);
}
