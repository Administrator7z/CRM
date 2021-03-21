package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.DicVlaueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.domain.Clue;
import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;
import com.bjpowernode.crm.workbench.service.ActivityService;
import com.bjpowernode.crm.workbench.service.ClueActivityRelationService;
import com.bjpowernode.crm.workbench.service.ClueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class ClueController {

    @Autowired
    private ClueService clueService;
    @Autowired
    private UserService userService;
    @Autowired
    private DicVlaueService dicValueService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private ClueActivityRelationService clueActivityRelationService;


    @RequestMapping("workbench/clue/index.do")
    public String index(Model model) {
        List<User> userList = userService.queryAllUsers();
        List<DicValue> appellationList = dicValueService.queryDicValueByTypeCode("appellation");
        List<DicValue> clueStateList = dicValueService.queryDicValueByTypeCode("clueState");
        List<DicValue> sourceList = dicValueService.queryDicValueByTypeCode("source");

        model.addAttribute("userList", userList);
        model.addAttribute("appellationList", appellationList);
        model.addAttribute("clueStateList", clueStateList);
        model.addAttribute("sourceList", sourceList);
        return "workbench/clue/index";
    }

    @RequestMapping("/workbench/clue/saveCreateClue.do")
    public @ResponseBody
    Object saveCreateClue(Clue clue, HttpSession session) {
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        clue.setId(UUIDUtils.getUUID());
        clue.setCreateBy(user.getId());
        clue.setCreateTime(DateUtils.formatDateTime(new Date()));

        ReturnObject returnObject = new ReturnObject();
        try {
            int ret = clueService.saveCreateClue(clue);
            if (ret > 0) {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            } else {
                returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
                returnObject.setMessage("系统忙，请稍后重试...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试...");
        }
        return returnObject;
    }

    @RequestMapping("/workbench/clue/detailClue.do")
    public String detailClue(String id, Model model) {
        Clue clue = clueService.queryClueForDetailById(id);
        List<Activity> activityList = activityService.queryActivityForDetailByClueId(id);
        model.addAttribute("clue", clue);
        model.addAttribute("activityList", activityList);
        return "workbench/clue/detail";
    }

    @RequestMapping("workbench/clue/searchActivity.do")
    @ResponseBody
    public Object searchActivity(String activityName, String clueId) {
        Map<String, Object> map = new HashMap<>();
        map.put("activityName", activityName);
        map.put("clueId", clueId);
        List<Activity> activityList = activityService.searchActivityNoBoundById(map);
        return activityList;
    }

    //添加绑定
    @RequestMapping("workbench/clue/saveBundActivity.do")
    @ResponseBody
    public Object saveBundActivity(String clueId, String[] activityId) {
        List<ClueActivityRelation> relationList = new ArrayList<>();
        ClueActivityRelation relation = null;
        ReturnObject returnObject = new ReturnObject();
        for (String s : activityId) {
            relation = new ClueActivityRelation();
            relation.setId(UUIDUtils.getUUID());
            relation.setClueId(clueId);
            relation.setActivityId(s);
            relationList.add(relation);
        }
        if (clueActivityRelationService.saveCreateClueActivityRelationByList(relationList) > 0) {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            List<Activity> activityList = activityService.queryActivityForDetailByIds(activityId);
            returnObject.setRetData(activityList);
        } else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("失败");

        }

        return returnObject;
    }

    //解除绑定
    @RequestMapping("workbench/clue/saveUnbundActivity.do")
    @ResponseBody
    public Object saveUnbundActivity(ClueActivityRelation relation) {
        ReturnObject returnObject = new ReturnObject();
        if (clueActivityRelationService.deleteClueActivityRelationByClueIdActivityId(relation) > 0) {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);

        } else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("");
        }
        return returnObject;

    }
    //线索转换跳转
    @RequestMapping("workbench/clue/convertClue.do")
    public String convertClue(String id,Model model){
        Clue clue = clueService.queryClueForDetailById(id);
        model.addAttribute("clue",clue);
        return "workbench/clue/convert";
    }
    //转换
    @RequestMapping("workbench/clue/saveConvertClue.do")
    @ResponseBody
    public Object saveConvertClue(String clueId,String isCreateTran,String amountOfMoney,String tradeName,String expectedClosingDate,
                                  String stage,String activityId,HttpSession session) {
        Map<String,Object> map=new HashMap<>();
        map.put("clueId",clueId);
        map.put("isCreateTran",isCreateTran);
        map.put("amountOfMoney",amountOfMoney);
        map.put("tradeName",tradeName);
        map.put("expectedClosingDate",expectedClosingDate);
        map.put("stage",stage);
        map.put("activityId",activityId);
        map.put("sessionUser",session.getAttribute(Contants.SESSION_USER));

        ReturnObject returnObject=new ReturnObject();
        try {
            //调用service层方法，保存线索转换
            clueService.saveConvert(map);
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }catch (Exception e){
            e.printStackTrace();
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("系统忙，请稍后重试....");
        }

        return returnObject;

    }





}
