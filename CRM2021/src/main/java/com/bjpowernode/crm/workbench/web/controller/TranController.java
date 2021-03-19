package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.workbench.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class TranController {

    @Autowired
    private CustomerService customerService;
    @RequestMapping("workbench/transaction/index.do")
    public String index(Model model){

        return "workbench/transaction/index";
    }
    @RequestMapping("workbench/transaction/typeahead.do")
    @ResponseBody
    public Object  typeahead(String query){
        return null;
    }

}
