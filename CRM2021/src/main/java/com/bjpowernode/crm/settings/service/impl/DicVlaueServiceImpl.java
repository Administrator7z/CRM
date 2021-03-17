package com.bjpowernode.crm.settings.service.impl;

import com.bjpowernode.crm.settings.domain.DicType;
import com.bjpowernode.crm.settings.domain.DicValue;
import com.bjpowernode.crm.settings.mapper.DicTypeMapper;
import com.bjpowernode.crm.settings.mapper.DicValueMapper;
import com.bjpowernode.crm.settings.service.DicVlaueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DicVlaueServiceImpl implements DicVlaueService {

    @Autowired
    private DicValueMapper dicValueMapper;
    @Autowired
    private DicTypeMapper dicTypeMapper;

    @Override
    public List<DicValue> queryAllDicValues() {
        return dicValueMapper.selectAllDicValues();
    }

    @Override
    public int saveCreateDicValue(DicValue dicValue) {
        return dicValueMapper.insertDicValue(dicValue);
    }

    @Override
    public DicValue queryDicValueById(String id) {
        return dicValueMapper.selectDicValueById(id);
    }

    @Override
    public int saveEditDicValue(DicValue dicValue) {
        return dicValueMapper.updateDicValue(dicValue);
    }

    @Override
    public int deleteDicValueByids(String[] ids) {
        return dicValueMapper.deleteDicValueByIds(ids);
    }

    @Override
    public List<DicValue> queryDicValueByTypeCode(String typeCode) {
        return dicValueMapper.selectDicValueByTypeCode(typeCode);
    }

    @Override
    public Map<String, List<DicValue>> getAll() {
        Map<String,List<DicValue>> map = new HashMap<>();
        List<DicType> dicList = dicTypeMapper.selectAllDicTypes();
        for (DicType dicType : dicList) {
            String code = dicType.getCode();
            List<DicValue> dicValueList  = dicValueMapper.selectDicValueByTypeCode(code);
            map.put(code+"list",dicValueList);
        }
        return map;
    }




}
