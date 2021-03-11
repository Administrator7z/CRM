package com.crm.service;

import com.bjpowernode.crm.commons.utils.MD5Util;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.crm.BaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class UserServiceTest extends BaseTest {
    @Autowired
    UserService userService;
    @Test
    public void testSelectUserById(){
        HashMap map = new HashMap();
        map.put("loginAct","ls");
        map.put("loginPwd","44ba5ca65651b4f36f1927576dd35436");
        User user = userService.queryUserByLoginActAndPwd(map);
        System.out.println(user.getName());
    }
    @Test
    public void testSelectAllUsers(){
        List<User> list = userService.queryAllUsers();
        for (Iterator<User> iterator = list.iterator(); iterator.hasNext(); ) {
            User next =  iterator.next();
            System.out.println(next.toString());

        }
    }
    @Test
    public void tt(){
        System.out.println(MD5Util.getMD5("zs"));

    }

}
