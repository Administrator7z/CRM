package com.bjpowernode.crm.workbench.mapper;


import com.bjpowernode.crm.workbench.domain.CustomerRemark;

import java.util.List;

public interface CustomerRemarkMapper {

    void insertCustomerRemarkByList(List<CustomerRemark> curList);
}