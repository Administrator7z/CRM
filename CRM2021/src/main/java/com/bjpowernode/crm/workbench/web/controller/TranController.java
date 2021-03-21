package com.bjpowernode.crm.workbench.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.mapper.DicValueMapper;
import com.bjpowernode.crm.settings.service.DicVlaueService;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.domain.TranRemark;
import com.bjpowernode.crm.workbench.service.CustomerService;
import com.bjpowernode.crm.workbench.service.TranRemarkService;
import com.bjpowernode.crm.workbench.service.TranService;
import com.bjpowernode.crm.workbench.service.TtranHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;


@Controller
public class TranController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private DicVlaueService dicVlaueService;
    @Autowired
    private UserService userService;
    @Autowired
    private TranService tranService;
    @Autowired
    private TranRemarkService tranRemarkService;
    @Autowired
    private TtranHistoryService tranHistoryService;




    //跳转
    @RequestMapping("workbench/transaction/index.do")
    public String index(Model model) {
        List<DicValue> stageList = dicVlaueService.queryDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList = dicVlaueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicVlaueService.queryDicValueByTypeCode("source");
        model.addAttribute("stageList", stageList);
        model.addAttribute("transactionTypeList", transactionTypeList);
        model.addAttribute("sourceList", sourceList);
        return "workbench/transaction/index";
    }


    @RequestMapping("workbench/transaction/createTran.do")
    public String typeahead(Model model) {
        List<User> userList = userService.queryAllUsers();
        List<DicValue> stageList = dicVlaueService.queryDicValueByTypeCode("stage");
        List<DicValue> transactionTypeList = dicVlaueService.queryDicValueByTypeCode("transactionType");
        List<DicValue> sourceList = dicVlaueService.queryDicValueByTypeCode("source");
        //把数据保存到request中
        model.addAttribute("userList", userList);
        model.addAttribute("stageList", stageList);
        model.addAttribute("transactionTypeList", transactionTypeList);
        model.addAttribute("sourceList", sourceList);
        return "workbench/transaction/save";
    }

    //阶段
    @RequestMapping("/workbench/transaction/queryCustomerByName.do")
    public @ResponseBody
    Object queryCustomerByName(String customerName) {
        List<Customer> customerList = customerService.queryCustomerByName(customerName);
        return customerList;
    }

    //根据key->value(分数）
    @RequestMapping("/workbench/transaction/getPossibilityByStageValue.do")
    public @ResponseBody
    Object getPossibilityByStageValue(String stageValue) {
        //读取资源properties
        ResourceBundle bundle = ResourceBundle.getBundle("possibility");
        String possible = bundle.getString(stageValue);
        return possible;
    }

    //保存交易
    @RequestMapping("/workbench/transaction/saveCreateTran.do")
    public @ResponseBody
    Object saveCreateTran(Tran tran, String customerName, HttpSession session) {
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        //封装参数
        tran.setId(UUIDUtils.getUUID());
        tran.setCreateBy(user.getId());
        tran.setCreateTime(DateUtils.formatDateTime(new Date()));

        Map<String, Object> map = new HashMap<>();
        map.put("tran", tran);
        map.put("customerName", customerName);
        map.put("sessionUser", user);

        ReturnObject returnObject = new ReturnObject();
        int num = tranService.saveCreateTran(map);
        if (num > 0) {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        } else {

        }

        return returnObject;
    }

    @RequestMapping("/workbench/transaction/detailTran.do")
    public String detailTran(String id, Model model) {
        //交易情况
        Tran tran=tranService.queryTranForDetailById(id);
        //交易备注
        List<TranRemark> remarkList=tranRemarkService.queryTranRemarkForDetailByTranId(id);
        //交易历史
        List<TranHistory> tranHistoryList=tranHistoryService.queryTranHistoryForDetailByTranId(id);

        //获取可能性
        ResourceBundle bundle=ResourceBundle.getBundle("possibility");
        String possibility=bundle.getString(tran.getStage());
        tran.setPossibility(possibility);

        //取所有的交易阶段
        List<DicValue> stageList=dicVlaueService.queryDicValueByTypeCode("stage");

        //model保存数据
        model.addAttribute("tran",tran);
        model.addAttribute("remarkList",remarkList);
        model.addAttribute("tranHistoryList",tranHistoryList);
        model.addAttribute("possibility",possibility);

        model.addAttribute("stageList",stageList);
        model.addAttribute("stageListLength",stageList.size());

        TranHistory tranHistory=null;
        for(int i=tranHistoryList.size()-1;i>0;i--){
            tranHistory=tranHistoryList.get(i); //0,1,2 get(2)最后成功的历史记录
            // <stageList.get(6)
            if(Integer.parseInt(tranHistory.getOrderNo())<Integer.parseInt(stageList.get(stageList.size()-3).getOrderNo())){
                model.addAttribute("theOrderNo",tranHistory.getOrderNo());
                break;
            }
        }


        return "workbench/transaction/detail";
    }

}
