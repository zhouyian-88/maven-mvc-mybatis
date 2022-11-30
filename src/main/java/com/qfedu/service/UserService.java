package com.qfedu.service;


import com.qfedu.pojo.User;
import com.qfedu.util.DataResult;

import java.util.Map;

public interface UserService {
    User loginUser(String userName, String userPwd);

    DataResult loadUserMessage(int pageNumber, int pageSize, User user);

    boolean addUser(Map<String,Object> userMap);

    boolean bachRemoveUser(String[] userIds);

    boolean modifyUser(User user);
}
