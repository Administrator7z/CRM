package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.ClueActivityRelation;

import java.util.List;

public interface ClueActivityRelationService {
    int saveCreateClueActivityRelationByList(List<ClueActivityRelation> relationList);
    //解除绑定
    int deleteClueActivityRelationByClueIdActivityId(ClueActivityRelation relation);
}
