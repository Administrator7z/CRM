package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicVlaueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class DicValueController {


    @Autowired
    private DicVlaueService dicVlaueService;

    @RequestMapping("settings/dictionary/value/index.do")
    public String index(Model model){
        List<DicValue> dicValueList = dicVlaueService.queryAllDicValues();
        model.addAttribute("dicValueList",dicValueList);
        return "settings/dictionary/value/index";
    }

}
