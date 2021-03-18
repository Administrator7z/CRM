package com.bjpowernode.crm.workbench.mapper;


import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityMapper {
    int insertActivity(Activity activity);

    List<Activity> selectActivityForPageByCondition(Map<String,Object> map);

    long selectCountOfActivityByCondition(Map<String,Object> map);

    Activity selectActivityById(String id);

    int updateActivity(Activity activity);

    //根据cludId查询这条线索相关联的市场活动明细
    List<Activity> selectActivityForDetailByClueId(String clueId);
}