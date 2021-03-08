package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.service.DicTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;


@Controller
public class DicTypeController {
    @Autowired
    private DicTypeService dicTypeService;

    @RequestMapping("settings/dictionary/type/index.do")
    public String index(Model model){
        List<DicType> dicTypeList = dicTypeService.queryAllDicTypes();
        model.addAttribute("dicTypeList",dicTypeList);
        return "settings/dictionary/type/index";
    }

    @RequestMapping("settings/dictionary/type/toSave.do")
    public String toSave(){
        return "settings/dictionary/type/save";
    }

    @RequestMapping(value = "settings/dictionary/type/saveCreateDicType.do")
    @ResponseBody
    public Object save(String code,String name,String description){
        DicType dicType = new DicType();
        dicType.setCode(code);
        dicType.setName(name);
        dicType.setDescription(description);
        ReturnObject returnObject = new ReturnObject();
        if (dicTypeService.saveCreateDicType(dicType) != 1 ){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("保存失败");
        }else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }
        return returnObject;
    }

    @RequestMapping("settings/dictionary/type/checkCode.do")
    @ResponseBody
    public Object verify(String code){
        DicType dicType = dicTypeService.queryDicTypeByCode(code);
        ReturnObject returnObject = new ReturnObject();
        if (dicType != null){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("id重复");
        }
        return returnObject;
    }

    @RequestMapping("settings/dictionary/type/editDicType.do")
    public String editDicType(String code,Model model){
        //调用service层方法，查询数据字典类型
        DicType dicType=dicTypeService.queryDicTypeByCode(code);
        //把数据保存到reuquest中
        model.addAttribute("dicType",dicType);
        //请求转发
        return "settings/dictionary/type/edit";
    }






}
