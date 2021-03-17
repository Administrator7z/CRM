package com.bjpowernode.crm.settings.mapper;

import com.bjpowernode.crm.settings.domain.DicValue;

import java.util.List;

public interface DicValueMapper {


    /**
     * 查询所有的数据字典值
     * @return
     */
    List<DicValue> selectAllDicValues();

    /**
     * 保存创建的数据字典值
     * @param dicValue
     * @return
     */
    int insertDicValue(DicValue dicValue);

    /**
    * 根据ids删除数据字典值
    */
    int deleteDicValueByIds(String[] ids);

    /**
    * 根据id查询数据字典值
    */
    DicValue selectDicValueById(String id);

    /**
    * 保存修改的数据字典值
    */
    int updateDicValue(DicValue dicValue);
    /**
    *根据typeCodes删除这些类型下的所有数据字典值
    */
    int deleteDicValueByTypeCodes(String[] typeCodes);
    /**
    * 根据typeCode查询数据字典值
    */
    List<DicValue> selectDicValueByTypeCode(String typeCode);
}