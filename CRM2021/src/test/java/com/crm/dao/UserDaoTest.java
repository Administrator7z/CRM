package com.crm.dao;

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.mapper.DicTypeMapper;
import com.bjpowernode.crm.settings.mapper.UserMapper;
import com.crm.BaseTest;
import org.apache.poi.poifs.crypt.HashAlgorithm;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class UserDaoTest extends BaseTest {

    @Autowired
    UserMapper userMapper;
    @Autowired
    DicTypeMapper dicTypeMapper;
    @Test
    public void selectUserByLoingActAndPwd(){
        HashMap map = new HashMap();
        map.put("loginAct","ls");
        map.put("loginPwd","44ba5ca65651b4f36f1927576dd35436");

        User user = userMapper.selectUserByLoingActAndPwd(map);
        System.out.println(user.getName());
    }

    @Test
    public void tt(){
        List<DicType> dicTypes = dicTypeMapper.selectAllDicTypes();
        for (DicType dicType : dicTypes) {
            System.out.println(dicType);
        }


    }
}
