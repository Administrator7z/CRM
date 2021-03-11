package com.bjpowernode.crm.workbench.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ActivityController {

    @RequestMapping("workbench/activity/index.do")
    public String index(){
        return "workbench/activity/index";
    }
}
//SELECT a.name,b.name,a.create_time,a.edit_time FROM tbl_activity a join tbl_user b where
//a.owner = b.id