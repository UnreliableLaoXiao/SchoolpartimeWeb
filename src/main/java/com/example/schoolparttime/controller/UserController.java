package com.example.schoolparttime.controller;

import com.example.schoolparttime.entity.Message;
import com.example.schoolparttime.entity.User;
import com.example.schoolparttime.entity.UserInfo;
import com.example.schoolparttime.entity.base.ResultModel;
import com.example.schoolparttime.filter.RequestFilter;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class UserController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 用户登录
     * @param signature
     * @param aesKey
     * @param token
     * @param data
     * @return
     */
    @ResponseBody
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/login", produces = "application/json;charset=UTF-8")
    public ResultModel Login(@RequestHeader(value = "Authentication") String signature,
                             @RequestHeader(value = "SecurityKey") String aesKey,
                             @RequestHeader(value = "TimesTamp") String token,
                             String data) {
        System.out.println("model is:" + data);
        final int result[] = {0};
        final User[] user = {null};
        try {
            RequestFilter.filterRequest(aesKey,data,signature,token,new RequestFilter.FilterResult(){

                @Override
                public void success(String body_json) {
                    Gson gson = new Gson();
                    User data_user = gson.fromJson(body_json,User.class);

                    List query = jdbcTemplate.query("select * from t_user where username = '"
                            + data_user.getUsername() + "' and password = '" + data_user.getPassword() + "'", new BeanPropertyRowMapper(User.class));
                    if (query != null){
                        user[0] = (User) query.get(0);
                        result[0] = 2;
                    }else {
                        result[0] = 1;
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

    /**
     * 注册账号信息
     * @param username
     * @param verifypsw
     * @param password
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/register", produces = "application/json;charset=UTF-8")
    public ResultModel registerUserInfo(String username, String verifypsw, String password) {
        System.out.println("username is:" + username);
        System.out.println("verifypsw is:" + verifypsw);
        System.out.println("password is:" + password);
        int rows = jdbcTemplate.update("insert into t_user (username,password,verifypsw,type) VALUES (?,?,?,?)",username,password,verifypsw,0);
        System.out.println(rows);
        if (rows > 0)
            return new ResultModel<User>("注册成功", null, "json", 200);
        else
            return new ResultModel<User>("数据插入错误，请看错误日志", null, "json", 400);
    }

    /**
     * 检查用户名是否存在
     * @param signature
     * @param aesKey
     * @param token
     * @param data
     * @return
     */
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

        System.out.println(username[0]);
        if (result[0] == 1) {
            List query = jdbcTemplate.query("select * from t_user where username = ?", new Object[]{username[0]}, new BeanPropertyRowMapper(User.class));
            if (query.size() == 0)
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

    /**
     * 修改密码
     * @param username
     * @param password
     * @param verifypsw
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/forget", produces = "application/json;charset=UTF-8")
    public ResultModel ForgetUserInfo(String username, String password, String verifypsw) {
        System.out.println("username is:" + username);
        System.out.println("token is:" + verifypsw);
        System.out.println("password is:" + password);
        List query = jdbcTemplate.query("select * from t_user where username = ? and verifypsw = ?",
                new Object[]{username, verifypsw}, new BeanPropertyRowMapper(User.class));
        if(query.size() > 0) {
            User user = (User) query.get(0);
            user.setPassword(password);
            int rows = jdbcTemplate.update("update t_user set password = ? where id = ?",user.getPassword(),user.getId());
            if (rows > 0){
                return new ResultModel<User>("修改成功", null, "json", 200);
            }else {
                return new ResultModel<User>("修改失败", null, "json", 300);
            }
        }else {
            return new ResultModel<User>("验证密码错误", null, "json", 400);
        }
    }

    /**
     * 完善个人信息
     * @param data
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/prefectinfo",produces = "application/json;charset=UTF-8")
    public ResultModel PrefectInfo(String data) {
        System.out.println("data is:" + data);
        Gson gson = new Gson();
        UserInfo userInfo = gson.fromJson(data, UserInfo.class);
        System.out.println("data is:" + userInfo.toString());
        int rows = jdbcTemplate.update("insert into t_u_info (id,address,phonenumber,type,userage,username,usersex) values (?,?,?,?,?,?,?)",
                userInfo.getId(),userInfo.getAddress(),userInfo.getPhonenumber(),userInfo.getType(),userInfo.getUserage(),userInfo.getUsername(),
                userInfo.getUsersex());
        if (rows == 0) {
            return new ResultModel<UserInfo>("信息完善失败", null, "json", 300);
        } else {
            long id = userInfo.getId();
            int row = jdbcTemplate.update("update t_user set type = ? where id = ?",1,id);
            if (row > 0){
                return new ResultModel<UserInfo>("信息完善成功", userInfo, "json", 200);
            }else {
                return new ResultModel<UserInfo>("信息完善失败", null, "json", 300);
            }

        }
    }

    /**
     * 数据库测试代码
     * @return
     */
    @RequestMapping("list")
    @ResponseBody
    public List<User> slist(){
        List<User> userList = jdbcTemplate.query("select * from t_user",new BeanPropertyRowMapper(User.class));
        File file = new File("text.txt");
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(file.getAbsolutePath());
        return userList;
    }


    public void saveMessage(Message message1) {
        int rows = jdbcTemplate.update("insert into t_message (mes,from,to,type,state) values(?,?,?,?,?)",
                message1.getMes(), message1.getFrom(), message1.getTo(), message1.getType(), message1.getState());
        System.out.println(rows);
    }

}
