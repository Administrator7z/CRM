package com.bjpowernode.crm.settings.mapper;

import com.bjpowernode.crm.settings.domain.DicType;

import java.util.List;

public interface DicTypeMapper {


    List<DicType> selectAllDicTypes();

    int insertDicType(DicType dicType);

    DicType selectDicTypeByCode(String code);

    int updateDicType(DicType dicType);

    int deleteDicTypeByCodes(String[] codes);


}