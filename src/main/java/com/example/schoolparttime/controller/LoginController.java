package com.example.schoolparttime.controller;

import com.example.schoolparttime.entity.User;
import com.example.schoolparttime.entity.base.ResultModel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {

    @ResponseBody
    @RequestMapping(value = "/login",produces = "application/json;charset=UTF-8")
    public ResultModel Login(String username,String token,String password){
        System.out.println("username is:"+username);
        System.out.println("token is:"+token);
        System.out.println("password is:"+password);
        User user = new User(1,"小黑","123456",123,1);
        return new ResultModel<User>("无数据返回",user,"json",200);
    }

    @ResponseBody
    @RequestMapping(value = "/register",produces = "application/json;charset=UTF-8")
    public ResultModel registerUserInfo() {
        User user = new User(1,"小黑","123456",123,1);
        return new ResultModel<User>("无数据返回",user,"json",200);
    }

}
