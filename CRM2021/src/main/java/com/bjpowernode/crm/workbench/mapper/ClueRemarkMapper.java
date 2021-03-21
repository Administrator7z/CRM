package com.bjpowernode.crm.workbench.mapper;


import com.bjpowernode.crm.workbench.domain.ClueRemark;

import java.util.List;

public interface ClueRemarkMapper {

    List<ClueRemark> selectClueRemarkByClueId(String clueId);

    void deleteClueRemarkByClueId(String clueId);
}