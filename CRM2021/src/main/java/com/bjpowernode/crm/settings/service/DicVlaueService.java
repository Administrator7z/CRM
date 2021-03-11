package com.bjpowernode.crm.settings.service;

import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;

public interface DicVlaueService {
    List<DicValue> queryAllDicValues();

    int saveCreateDicValue(DicValue dicValue);

    DicValue queryDicValueById(String id);

    int  saveEditDicValue(DicValue dicValue);

    int deleteDicValueByids(String[] ids);
}
