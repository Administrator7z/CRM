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
    //查询未绑定的市场活动
    List<Activity> searchActivityNoBoundById(Map<String, Object> map);
    //根据ids查询市场活动
    List<Activity> selectActivityForDetailByIds(String[] ids);
    //搜索全部 下载用
    List<Activity> selectAllActivityForDetail();
}