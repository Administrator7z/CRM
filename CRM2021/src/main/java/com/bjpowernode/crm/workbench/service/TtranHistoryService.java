package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.TranHistory;

import java.util.List;

public interface TtranHistoryService {
    List<TranHistory> queryTranHistoryForDetailByTranId(String id);
}
