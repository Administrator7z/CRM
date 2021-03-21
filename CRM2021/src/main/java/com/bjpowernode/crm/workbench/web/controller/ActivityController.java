package com.bjpowernode.crm.workbench.web.controller;


import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.DateUtils;
import com.bjpowernode.crm.commons.utils.UUIDUtils;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import com.bjpowernode.crm.workbench.domain.Activity;
import com.bjpowernode.crm.workbench.service.ActivityService;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Controller
public class ActivityController {

    @Autowired
    private UserService userService;

    @Autowired
    private ActivityService activityService;


    @RequestMapping("workbench/activity/index.do")
    public String index(Model model){
        List<User> userList = userService.queryAllUsers();
        model.addAttribute("userList",userList);
        return "workbench/activity/index";
    }


    @RequestMapping("workbench/activity/saveCreateActivity.do")
    @ResponseBody
    public Object saveCreateActivity(Activity activity, HttpSession session){
        ReturnObject returnObject = new ReturnObject();
        User user = (User) session.getAttribute(Contants.SESSION_USER);
        activity.setId(UUIDUtils.getUUID());
        activity.setCreateTime(DateUtils.formatDateTime(new Date()));
        activity.setCreateBy(user.getId());
        if (activityService.saveCreateActivity(activity)>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("失败");
        }
        return returnObject;

    }
    @RequestMapping("workbench/activity/queryActivityForPageByCondition.do")
    @ResponseBody
    public Object queryActivityForPageByCondition(int pageNo,int pageSize,String name,String owner,String startDate,String endDate){
        Map<String,Object> map = new HashMap<>();
        map.put("pageNo",(pageNo-1) * pageSize );
        map.put("pageSize",pageSize);
        map.put("name",name);
        map.put("owner",owner);
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        List<Activity> activityList=activityService.queryActivityForPageByCondition(map);

        long totalRows=activityService.queryCountOfActivityByCondition(map);

        Map<String,Object> retMap=new HashMap<>();
        retMap.put("activityList",activityList);
        retMap.put("totalRows",totalRows);
        return retMap;

    }


    @RequestMapping("workbench/activity/editActivity.do")
    public @ResponseBody Object editActivity(String id){
        Activity activity=activityService.queryActivityById(id);
        return activity;
    }

    @RequestMapping("workbench/activity/saveEditActivity.do")
    public @ResponseBody Object saveEditActivity(Activity activity,HttpSession session){
        User user=(User)session.getAttribute(Contants.SESSION_USER);
        activity.setEditBy(user.getId());
        activity.setEditTime(DateUtils.formatDateTime(new Date()));

        ReturnObject returnObject=new ReturnObject();

        int ret=activityService.saveEditActivity(activity);

        if(ret>0){
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        }else{
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMessage("失败");
        }

        return returnObject;

    }
    //下载
    @RequestMapping("/workbench/activity/exportAllActivity.do")
    public void exportAllActivity(HttpServletRequest request, HttpServletResponse response) throws Exception{
        //调用service,查询所有的市场活动
        List<Activity> activityList=activityService.queryAllActivityForDetail();
        //1.创建工作薄
        HSSFWorkbook wb=new HSSFWorkbook();
        //2.工作表
        HSSFSheet sheet=wb.createSheet("市场活动列表");
        //3.创建第一行做为标题行
        HSSFRow row=sheet.createRow(0);
        //4.获取多个列
        HSSFCell cell=row.createCell(0);
        cell.setCellValue("ID");
        cell=row.createCell(1);
        cell.setCellValue("所有者");
        cell=row.createCell(2);
        cell.setCellValue("名称");
        cell=row.createCell(3);
        cell.setCellValue("开始日期");
        cell=row.createCell(4);
        cell.setCellValue("结束日期");
        cell=row.createCell(5);
        cell.setCellValue("成本");
        cell=row.createCell(6);
        cell.setCellValue("描述");
        cell=row.createCell(7);
        cell.setCellValue("创建者");
        cell=row.createCell(8);
        cell.setCellValue("创建时间");
        cell=row.createCell(9);
        cell.setCellValue("修改者");
        cell=row.createCell(10);
        cell.setCellValue("修改时间");

        //创建样式
        HSSFCellStyle style=wb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);

        //遍历activityList,显示数据行
        if(activityList!=null){
            Activity activity=null;
            for(int i=0;i<activityList.size();i++){
                //获取每一条activity数据
                activity=activityList.get(i);
                row=sheet.createRow(i+1);
                cell=row.createCell(0);
                cell.setCellValue(activity.getId());
                cell=row.createCell(1);
                cell.setCellValue(activity.getOwner());
                cell=row.createCell(2);
                cell.setCellValue(activity.getName());
                cell=row.createCell(3);
                cell.setCellValue(activity.getStartDate());
                cell=row.createCell(4);
                cell.setCellValue(activity.getEndDate());
                cell=row.createCell(5);
                cell.setCellValue(activity.getCost());
                cell=row.createCell(6);
                cell.setCellValue(activity.getDescription());
                cell=row.createCell(7);
                cell.setCellValue(activity.getCreateBy());
                cell=row.createCell(8);
                cell.setCellValue(activity.getCreateTime());
                cell=row.createCell(9);
                cell.setCellValue(activity.getEditBy());
                cell=row.createCell(10);
                cell.setCellValue(activity.getEditTime());
            }
        }

        //创建IO流，通过浏览器来下载
        //1.设置响应类型
        response.setContentType("application/octet-stream;charset=UTF-8");
        //判断用户的浏览器
        String browser=request.getHeader("User-Agent");
        //IE浏览器urlencoded
        String fileName= URLEncoder.encode("市场活动列表","UTF-8");
//        if(browser.contains("firefox")){
//            //火狐iso8859-1
//            fileName=new String("市场活动列表".getBytes("UTF-8"),"ISO8859-1");
//        }

        //2.设置响应头信息，让浏览器直接下载
        response.addHeader("Content-Disposition","attachment;filename="+fileName+".xls");

        //3.获取输出流
        OutputStream os2=response.getOutputStream();
        wb.write(os2);
        os2.flush();
        os2.close();
        wb.close();


    }

    //对上传文件的处理
    @RequestMapping("/workbench/activity/fileUpload.do")
    public @ResponseBody Object fileUpload(String username, MultipartFile myFile) throws Exception{
        System.out.println(username);

        //获取上传的文件名
        String originalFilename=myFile.getOriginalFilename();
        //将上传过来的文件保存到服务器的硬盘
        File file=new File("d:\\testDir",originalFilename);
        //传输文件
        myFile.transferTo(file);
        ReturnObject returnObject=new ReturnObject();
        returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
        return returnObject;
    }
}
