package com.bjpowernode.crm.settings.web.controller;

import com.bjpowernode.crm.commons.contants.Contants;
import com.bjpowernode.crm.commons.domain.ReturnObject;
import com.bjpowernode.crm.commons.utils.MD5Util;
import com.bjpowernode.crm.settings.domain.User;
import com.bjpowernode.crm.settings.service.UserService;
import javafx.application.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.lang.model.element.NestingKind;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {


    @Autowired
    private UserService userService;
    private String loginPwdMD5;

    @RequestMapping("/settings/qx/user/toLogin.do")
    public String toLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String loginAct = null;
        String loginPwd = null;
        for (Cookie cookie : cookies) {
            if ("loginAct".equals(cookie.getName())){
                loginAct = cookie.getValue();
                continue;
            }
            if ("loginPwd".equals(cookie.getName())){
                loginPwd = cookie.getValue();
            }
        }
        if (loginAct != null && loginPwd !=null){
            Map<String,Object> map = new HashMap<>();
            map.put("loginAct", loginAct);
            map.put("loginPwd", loginPwd);
            User user = userService.queryUserByLoginActAndPwd(map);
            request.getSession().setAttribute(Contants.SESSION_USER, user);
            return "redirect:/workbench/index.do";
        }else {
            return "settings/qx/user/login";
        }

    }



    @RequestMapping("settings/qx/user/login.do")
    @ResponseBody
    public Object login(String loginAct, String loginPwd, String isRemPwd, HttpServletResponse response, HttpSession session) {

        HashMap<String, Object> map = new HashMap<>();
        loginPwdMD5 = MD5Util.getMD5(loginPwd);
        map.put("loginAct", loginAct);
        map.put("loginPwd", loginPwdMD5);
        User user = userService.queryUserByLoginActAndPwd(map);
        ReturnObject returnObject = new ReturnObject();
        if (user == null) {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMassage("账号密码错误");
        } else if ("0".equals(user.getLockState())) {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_FAIL);
            returnObject.setMassage("账号锁定");
        } else {
            returnObject.setCode(Contants.RETURN_OBJECT_CODE_SUCCESS);
            session.setAttribute(Contants.SESSION_USER, user);
        }
        if ("true".equals(isRemPwd)) {
            Cookie loginA = new Cookie("loginAct", loginAct);
            Cookie loginP = new Cookie("loginPwd", loginPwdMD5);
            response.addCookie(loginA);
            response.addCookie(loginP);
            loginA.setMaxAge(24 * 60 * 60 * 10);
            loginP.setMaxAge(24 * 60 * 60 * 10);

        } else {
            Cookie c1 = new Cookie("loginAct", null);
            c1.setMaxAge(0);
            response.addCookie(c1);
            Cookie c2 = new Cookie("loginPwd", null);
            c2.setMaxAge(0);
            response.addCookie(c2);
        }
        System.out.println(returnObject.toString());
        return returnObject;

    }

    @RequestMapping("settings/qx/user/logout.do")
    public String logOut(HttpServletResponse response,HttpSession session){
        Cookie c1 = new Cookie("loginAct", null);
        c1.setMaxAge(0);
        response.addCookie(c1);
        Cookie c2 = new Cookie("loginPwd", null);
        c2.setMaxAge(0);
        response.addCookie(c2);
        session.invalidate();
        return "redirect:/";
    }

}
