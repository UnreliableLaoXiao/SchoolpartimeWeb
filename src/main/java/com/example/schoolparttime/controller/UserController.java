package com.example.schoolparttime.controller;

import com.example.schoolparttime.dao.UInfoRepository;
import com.example.schoolparttime.dao.UserRepository;
import com.example.schoolparttime.entity.User;
import com.example.schoolparttime.entity.UserInfo;
import com.example.schoolparttime.entity.base.ResultModel;
import com.example.schoolparttime.filter.RequestFilter;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    // 注入userRepository
    @Autowired
    private UserRepository userRepository;



    @ResponseBody
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/login", produces = "application/json;charset=UTF-8")
    public ResultModel Login(@RequestHeader(value = "Authentication") String signature,
                             @RequestHeader(value = "SecurityKey") String aesKey,
                             @RequestHeader(value = "TimesTamp") String token,
                             String data) {
        System.out.println("model is:" + data);
        final int[] result = {0};
        final User[] user = {null};
        try {
            RequestFilter.filterRequest(aesKey,data,signature,token,new RequestFilter.FilterResult(){

                @Override
                public void success(String body_json) {
                    Gson gson = new Gson();
                    User data_user = gson.fromJson(body_json,User.class);
                    user[0] = userRepository.findByUsernameAndPassword(data_user.getUsername(), data_user.getPassword());
                    if (user[0] == null) {
                        result[0] = 1;
                    } else {
                        result[0] = 2;
                    }
                }

                @Override
                public void fail() {
                    result[0] = 3;
                }

                @Override
                public void timeout() {
                    result[0] = 4;
                }
            });

            if (result[0] == 1) {
                return new ResultModel<User>("账号或密码错误", null, "json", 400);
            } else if (result[0] == 2) {
                return new ResultModel<User>("登录成功", user[0], "json", 200);
            } else if (result[0] == 3){
                return new ResultModel<User>("证书错误，请求失败", null, "json", 300);
            } else {
                return new ResultModel<User>("证书错误，请求失败", null, "json", 300);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return new ResultModel<User>("服务器异常", null, "json", 500);
        }
    }

    @ResponseBody
    @RequestMapping(value = "/register", produces = "application/json;charset=UTF-8")
    public ResultModel registerUserInfo(String username, String verifypsw, String password) {
        System.out.println("username is:" + username);
        System.out.println("verifypsw is:" + verifypsw);
        System.out.println("password is:" + password);
        User user = new User(0, username, password, verifypsw, 1);
        User save = userRepository.save(user);
        if (save != null)
            return new ResultModel<User>("注册成功", null, "json", 200);
        else
            return new ResultModel<User>("数据插入错误，请看错误日志", null, "json", 400);
    }

    @ResponseBody
    @RequestMapping(value = "/checkusernameisexist", produces = "application/json;charset=UTF-8")
    public ResultModel checkUsernameIsExist(@RequestHeader(value = "Authentication") String signature,
                                            @RequestHeader(value = "SecurityKey") String aesKey,
                                            @RequestHeader(value = "TimesTamp") String token,
                                            String data) {
        System.out.println("username is:" + data);
        final int[] result = {0};
        final String[] username = {"a"};
        try {
            RequestFilter.filterRequest(aesKey, data, signature, token, new RequestFilter.FilterResult() {
                @Override
                public void success(String str) {
                    result[0] = 1;
                    username[0] = str;
                }

                @Override
                public void fail() {
                    result[0] = 2;
                }

                @Override
                public void timeout() {
                    result[0] = 3;
                }
            });
        } catch (Exception e) {
            result[0] = 4;
            e.printStackTrace();
        }
        if (result[0] == 1) {
            User user = userRepository.findByUsername(username[0]);
            if (user == null)
                return new ResultModel<User>("不存在，可以注册", null, "json", 200);
            else
                return new ResultModel<User>("已存在，无法注册", null, "json", 400);
        } else if (result[0] == 2) {
            return new ResultModel<User>("证书错误，请求失败", null, "json", 300);
        } else if (result[0] == 3){
            return new ResultModel<User>("证书错误，请求失败", null, "json", 300);
        } else {
            return new ResultModel<User>("服务器异常", null, "json", 500);
        }




    }

    @ResponseBody
    @RequestMapping(value = "/forget", produces = "application/json;charset=UTF-8")
    public ResultModel ForgetUserInfo(String username, String password, String verifypsw) {
        System.out.println("username is:" + username);
        System.out.println("token is:" + verifypsw);
        System.out.println("password is:" + password);
        User user = userRepository.findByUsernameAndVerifypsw(username, verifypsw);
        if (user == null) {
            return new ResultModel<User>("验证密码错误", null, "json", 400);
        } else {
            user.setPassword(password);
            userRepository.save(user);
            return new ResultModel<User>("修改成功", null, "json", 200);
        }


    }

    @Autowired
    private UInfoRepository uInfoRepository;


    @ResponseBody
    @RequestMapping(value = "/prefectinfo",produces = "application/json;charset=UTF-8")
    public ResultModel PrefectInfo(String data) {
        System.out.println("data is:" + data);
        Gson gson = new Gson();
        UserInfo userInfo = gson.fromJson(data, UserInfo.class);
        System.out.println("data is:" + userInfo.toString());
        UserInfo save = uInfoRepository.save(userInfo);
        if (save == null) {
            return new ResultModel<UserInfo>("验证密码错误", null, "json", 400);
        } else {
            long id = userInfo.getId();
            User userById = userRepository.findUserById(id);
            userById.setType(1);
            userRepository.save(userById);
            return new ResultModel<UserInfo>("信息完善成功", save, "json", 200);
        }
    }

}
