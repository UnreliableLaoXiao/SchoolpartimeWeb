package com.example.schoolparttime.controller;

import com.example.schoolparttime.dao.UserRepository;
import com.example.schoolparttime.entity.User;
import com.example.schoolparttime.entity.base.RequestModel;
import com.example.schoolparttime.entity.base.ResultModel;
import com.example.schoolparttime.security.aes.AESUtil;
import com.example.schoolparttime.security.md5.Md5Util;
import com.example.schoolparttime.security.rsa.RSAUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.util.Base64;

@Controller
public class LoginController {
    // 注入userRepository
    @Autowired
    private UserRepository userRepository;

    @ResponseBody
    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/login",produces = "application/json;charset=UTF-8")
    public ResultModel Login(@RequestHeader(value="Authentication") String signature,
                             @RequestHeader(value="SecurityKey") String aesKey,
                             @RequestHeader(value="TimesTamp") String token,
                             String data ){
//        data = data.replace("\r\n", "");
//        signature = signature.replace("\r\n", "");
//        aesKey = aesKey.replace("\r\n", "");
        System.out.println("model is:"+data);
        System.out.println("signature is:"+signature);
        System.out.println("aesKey is:"+aesKey);
        System.out.println("token is:"+token);

         String privateKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDB3J/fk1wOthc12zn9OoKiQuUE"
                + "JUqkP8cFhPTJ52dRppQ/p84ALxxunkU5qeqvYKafKpXJS+454bBeCIgwapefw4BgCg3atQy7IMiMhAQrzOWffMkKsF"
                + "pokOLrf36TvXUoXDEC9NGWMkQcGBHXVtsB1S4SPkTui4daJd1dSC3/s9X1lpGFXIq9lftFTjZjpPyR48ZC3KNurjxKz"
                + "cE1SPzrU8CDehq8AXb/zG9GecJis0qBKdu7EeRoWO+38+OdRRyoCdoAnUYjl0dQao1vJeL5n2fJivP4efXkr0btflho"
                + "7/AoeACpvQfVSqR2UHmKd70PK7restsPoeYWkRqOLIzjAgMBAAECggEAUf0B7GeBJoOiW1elNdC6EO/jtZYj9EU44b+y"
                + "W3Wvf5vI1QceG3vRNYNgWZvgMl3Y+jXjdWfUj8xAb/SBzKA4Egx3zaZS561sffPGfY8TyIZ2krYOvKOLCPBF2D1qhgc5"
                + "dmFPJSXvQetuXMddPEpyg1rqijKlqpF+JAUhkuME+UATvtUalLYiE3heOpdxYrOnGe2jdrLE4wYoNVc4HOkTUmsRrO8GS"
                + "iemmFp7gFJEki5DbjmVYRw+EuLb0/Pntf30k2xS0PKaM1JMMErnhGpUD46gRCwW7KMnJMmpGmP0fqLTT7mmOLSbf7JP89t"
                + "QFnihAmkpvgihclCqHE8waRpW+QKBgQDrzSP1dBLJOKpu2aZwB1eSYAvUgeSgF1FNAtKeq7j3U2qo8Q0++6/B2RWzGQuu58"
                + "AvhUvM6FMYAnny0PWtw4avdM80uGeafdsnwCIEAiEGg3YuwpqVe5zjzSztkLjGL3tEXDSoYBjuTR85DRvUzGe8V+W49Rk5/9"
                + "PbeMOk91hexQKBgQDSd8wfhzf6av1uvrXcOwYkDLBMhi2dVqbETO16x2WzC3rGIIbuHtMZmE8pXEmTWff5+nagEMBgSXOszsJ"
                + "5QeKb2UTzds1t5RJ6JZqx1NEiTozA961slbCU9dE6MJEVN1QWEulDvjjWdwnGenmS6MhYBoJiFlr3XYknZY0huMF3hwKBgAiVt"
                + "WNwUGbHC11xyx91BPktSgD4oaw6bRlSqvxf4CIRBWcVL5hFbYavMp0MomJBybtxLOtO4geTv4DZnrgu0C5/IDQZKpxzTJFL63Ed"
                + "6rnj+1+EckBS+clJZQNZK4D7pY89lCU1KnMyl5pqIcNDldtDj/eF5N85syrgYK8W2j7JAoGBAKbxf2hCyZRI6V2+yGI4L1bI+c65"
                + "X9U9tmpe2sBZCcJLiMc/Zcfbi3bx6VjVa0cGRjxy/0VYBEBcAvU/y+KC8EzOunKj+a8B1PYufdYxCPI9fEhULavD0J1Xnu6ZN3u"
                + "st3YK6hxh9pOnOInG/EgbfU7VWvaS5PTxrKkjVB87fbphAoGBAIdv+4tIfElaecYYz0Qqu7TJ5KqV2qdGVzIGDGOnVEMElvOwJ"
                + "2rMGt5slpQTmdkuZG8IDBXaxK2z6T0BC3awbQMz5a3PJopEcX5TDclbcWFV1ogpe1hqgDk8obhnoZA+zNdH8APJa6WcEtqV6myz4"
                + "FQNib0fbWHiKwGXSkcXpqD8";

        try {
            PrivateKey privateKey = RSAUtil.string2Privatekey(privateKeyStr);

            byte[] privateDecryBytes = RSAUtil.privateDecrypt(Base64.getDecoder().decode(aesKey), privateKey);
            System.out.println("privateDecryBytes size：" + privateDecryBytes.length);
            String AESKeySecurity_now = new String(privateDecryBytes);

            System.out.println("私钥解密后的AES原始字符串：" + AESKeySecurity_now);

            SecretKey secretKey1 = AESUtil.strKey2SecretKey(AESKeySecurity_now);
            String body_json = new String(AESUtil.decryptAES(Base64.getDecoder().decode(data), secretKey1), "utf-8");

            System.out.println("解密后的数据为：" + body_json);

            byte[] privateDecryBytes_signature = RSAUtil.privateDecrypt(Base64.getDecoder().decode(signature), privateKey);
            String md5_old = new String(privateDecryBytes_signature);
            System.out.println("私钥解密后的md5原始字符串：" + md5_old);



            String md5_now = Md5Util.build(body_json);

            System.out.println("签名是否相同"+md5_now.equals(md5_old));

        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println("username is:"+username);
//        System.out.println("password is:"+password);
//        User user = userRepository.findByUsernameAndPassword(username,password);
//        if (user == null){
//            return new ResultModel<User>("账号或密码错误", null, "json", 400);
//        }else {
//            return new ResultModel<User>("无数据返回", user, "json", 200);
//        }



        return new ResultModel<User>("账号或密码错误", null, "json", 400);
    }





    @ResponseBody
    @RequestMapping(value = "/register",produces = "application/json;charset=UTF-8")
    public ResultModel registerUserInfo(String username,String verifypsw,String password) {
        System.out.println("username is:"+username);
        System.out.println("verifypsw is:"+verifypsw);
        System.out.println("password is:"+password);
        User user = new User(0,username,password,verifypsw,1);
        User save = userRepository.save(user);
        if (save != null)
            return new ResultModel<User>("注册成功",null,"json",200);
        else
            return new ResultModel<User>("数据插入错误，请看错误日志",null,"json",400);
    }

    @ResponseBody
    @RequestMapping(value = "/checkusernameisexist",produces = "application/json;charset=UTF-8")
    public ResultModel checkUsernameIsExist(String username) {
        System.out.println("username is:"+username);
        User user = userRepository.findByUsername(username);
        if (user == null)
            return new ResultModel<User>("不存在，可以注册",null,"json",200);
        else
            return new ResultModel<User>("已存在，无法注册",null,"json",400);
    }

    @ResponseBody
    @RequestMapping(value = "/forget",produces = "application/json;charset=UTF-8")
    public ResultModel ForgetUserInfo(String username,String password,String verifypsw) {
        System.out.println("username is:"+username);
        System.out.println("token is:"+verifypsw);
        System.out.println("password is:"+password);
        User user = userRepository.findByUsernameAndVerifypsw(username,verifypsw);
        if (user == null){
            return new ResultModel<User>("验证密码错误",null,"json",400);
        } else {
            user.setPassword(password);
            userRepository.save(user);
            return new ResultModel<User>("修改成功",null,"json",200);
        }



    }

}
