package com.bjpowernode.crm.workbench.mapper;


import com.bjpowernode.crm.workbench.domain.Clue;

public interface ClueMapper {

    /**
     * 保存创建的线索
     */
    int insertClue(Clue clue);

    /**
     * 根据id查询线索明细信息
     */
    Clue selectClueForDetailById(String id);
    //根据id查询线索信息
    Clue selectClueById(String clueId);
}