package com.bjpowernode.crm.workbench.mapper;


import com.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TranHistoryMapper {

    void insertTranHistory(TranHistory tranHistory);

    List<TranHistory> selectTranHistoryForDetailByTranId(String tranId);
}