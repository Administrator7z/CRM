package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.service.DicTypeService;
import com.bjpowernode.crm.settings.service.DicVlaueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class DicValueController {

    @Autowired
    private DicTypeService dicTypeService;
    @Autowired
    private DicVlaueService dicVlaueService;

    @RequestMapping("settings/dictionary/value/index.do")
    public String index(Model model){
        List<DicValue> dicValueList = dicVlaueService.queryAllDicValues();
        model.addAttribute("dicValueList",dicValueList);
        return "settings/dictionary/value/index";
    }

    @RequestMapping("settings/dictionary/value/toSave.do")
    public String toSave(Model model) {
        List<DicType> dicTypeList = dicTypeService.queryAllDicTypes();
        model.addAttribute("dicTypeList", dicTypeList);
        return "settings/dictionary/value/save";
    }

    @RequestMapping("/settings/dictionary/value/saveCreateDicValue.do")
    public @ResponseBody Object saveCreateDicValue(DicValue dicValue){
        //封装参数
        dicValue.setId(UUIDUtils.getUUID());

        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service层方法，保存数据
            int ret = dicVlaueService.saveCreateDicValue(dicValue);
            if(ret>0){
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            }else{
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试....");
            }
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }
        return returnObject;
    }





}
