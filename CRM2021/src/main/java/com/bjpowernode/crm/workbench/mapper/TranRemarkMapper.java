package com.bjpowernode.crm.workbench.mapper;


import com.bjpowernode.crm.workbench.domain.TranRemark;

import java.util.List;

public interface TranRemarkMapper {

    void insertTranRemarkByList(List<TranRemark> trList);

    List<TranRemark> selectTranRemarkForDetailByTranId(String tranId);
}