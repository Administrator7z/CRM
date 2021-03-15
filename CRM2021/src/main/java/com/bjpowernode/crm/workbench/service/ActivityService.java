package com.bjpowernode.crm.workbench.service;

import com.bjpowernode.crm.workbench.domain.Activity;

import java.util.List;
import java.util.Map;

public interface ActivityService {
    int saveCreateActivity(Activity activity);

    List<Activity> queryActivityForPageByCondition(Map<String,Object> map);

    long queryCountOfActivityByCondition(Map<String,Object> map);

    Activity queryActivityById(String id);

    int saveEditActivity(Activity activity);


}
