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
    public String index(Model model) {
        List<DicType> dicTypeList = dicTypeService.queryAllDicTypes();
        model.addAttribute("dicTypeList", dicTypeList);
        return "settings/dictionary/type/index";
    }

    @RequestMapping("settings/dictionary/type/toSave.do")
    public String toSave() {
        return "settings/dictionary/type/save";
    }

    @RequestMapping(value = "settings/dictionary/type/saveCreateDicType.do")
    @ResponseBody
    public Object saveCreateDicType(DicType dicType) {
        ReturnObject returnObject = new ReturnObject();
        if (dicTypeService.saveCreateDicType(dicType) > 0) {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        } else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("保存失败");
        }
        return returnObject;
    }

    @RequestMapping("settings/dictionary/type/checkCode.do")
    @ResponseBody
    public Object checkCode(String code) {
        DicType dicType = dicTypeService.queryDicTypeByCode(code);
        ReturnObject returnObject = new ReturnObject();
        if (dicType == null) {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        } else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("id重复");
        }
        return returnObject;
    }

    @RequestMapping("settings/dictionary/type/editDicType.do")
    public String editDicType(String code, Model model) {
        DicType dicType = dicTypeService.queryDicTypeByCode(code);
        model.addAttribute("dicType", dicType);
        return "settings/dictionary/type/edit";
    }

    @RequestMapping("settings/dictionary/type/saveEditDicType.do")
    @ResponseBody
    public Object saveEditDicType(DicType dicType) {
        ReturnObject returnObject = new ReturnObject();
        if (dicTypeService.saveEditDicType(dicType) > 0) {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        } else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("更新失败");
        }

        return returnObject;
    }

    @RequestMapping("settings/dictionary/type/deleteDicTypeByCodes.do")
    @ResponseBody
    public Object deleteDicTypeByCodes(String[] code) {
        ReturnObject returnObject = new ReturnObject();
        if (dicTypeService.deleteDicTypeByCodes(code) > 0) {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        } else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("忙");
        }
        return returnObject;
    }


}
