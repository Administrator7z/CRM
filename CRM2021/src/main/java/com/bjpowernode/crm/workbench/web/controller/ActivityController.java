package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ActivityController {

    @Autowired
    private UserService userService;

    @RequestMapping("workbench/activity/index.do")
    public String index(Model model){
        List<User> userList = userService.queryAllUsers();
        model.addAttribute("userList",userList);
        return "workbench/activity/index";
    }
}
//SELECT a.name,b.name,a.create_time,a.edit_time FROM tbl_activity a join tbl_user b where
//a.owner = b.id