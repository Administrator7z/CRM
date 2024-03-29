package com.bjpowernode.crm.workbench.service.impl;

import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;
import com.bjpowernode.crm.workbench.domain.TranHistory;
import com.bjpowernode.crm.workbench.mapper.CustomerMapper;
import com.bjpowernode.crm.workbench.mapper.TranHistoryMapper;
import com.bjpowernode.crm.workbench.mapper.TranMapper;
import com.bjpowernode.crm.workbench.service.TranService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class TranServiceImpl implements TranService {

    @Autowired
    private TranMapper tranMapper;
    @Autowired
    private TranHistoryMapper tranHistoryMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Override
    public int saveCreateTran(Map<String, Object> map) {
        //需要客户是否新
        Tran tran=(Tran)map.get("tran");
        //客户id
        String customerId=tran.getCustomerId();
        String customerName=(String)map.get("customerName");
        User user=(User)map.get("sessionUser");

        //是否需要创建客户
        if(customerId==null||customerId.trim().length()==0){
            //没有客户
            Customer customer=new Customer();
            customer.setName(customerName);
            customer.setId(UUIDUtils.getUUID());
            customer.setOwner(user.getId());
            customer.setCreateTime(DateUtils.formatDateTime(new Date()));
            customer.setCreateBy(user.getId());

            customerMapper.insertCustomer(customer);
            //只有插入customer之后，才知道新客户customerId,要在交易中customerId属性
            tran.setCustomerId(customer.getId());
        }
        //保存交易
        tranMapper.insert(tran);
        //保存交易历史记录
        TranHistory tranHistory=new TranHistory();
        tranHistory.setCreateBy(user.getId());
        tranHistory.setCreateTime(DateUtils.formatDateTime(new Date()));
        tranHistory.setExpectedDate(tran.getExpectedDate());
        tranHistory.setId(UUIDUtils.getUUID());
        tranHistory.setMoney(tran.getMoney());
        tranHistory.setStage(tran.getStage());
        tranHistory.setTranId(tran.getId());
        tranHistoryMapper.insertTranHistory(tranHistory);

        return 0;
    }
    @Override
    public Tran queryTranForDetailById(String id) {
        return tranMapper.selectTranForDetailById(id);
    }

}
