package com.bjpowernode.crm.workbench.mapper;

import com.bjpowernode.crm.workbench.domain.Customer;
import com.bjpowernode.crm.workbench.domain.Tran;

import java.util.List;

public interface TranMapper {
    List<Tran> selectAll(String name);
}