package com.crm.dao;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.crm.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public class UserDaoTest extends BaseTest {

    @Autowired
    UserMapper userMapper;
    @Test
    public void selectUserByLoingActAndPwd(){
        HashMap map = new HashMap();
        map.put("loginAct","ls");
        map.put("loginPwd","44ba5ca65651b4f36f1927576dd35436");

        User user = userMapper.selectUserByLoingActAndPwd(map);
        System.out.println(user.getName());
    }
}
