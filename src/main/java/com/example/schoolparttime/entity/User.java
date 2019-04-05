package com.example.schoolparttime.entity;

import com.example.schoolparttime.entity.base.RequestModel;
import com.example.schoolparttime.security.md5.Md5Util;
import com.example.schoolparttime.security.rsa.RSAUtil;
import com.google.gson.Gson;

import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;

public class User {
    private long id;

    private String username;

    private String password;

    private String verifypsw;

    private int type = 0;

    public User() {
    }

    public User(long id , String username, String password, String verifypsw, int type) {
        this.username = username;
        this.password = password;
        this.verifypsw = verifypsw;
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getverifypsw() {
        return verifypsw;
    }

    public void setverifypsw(String verifypsw) {
        this.verifypsw = verifypsw;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    private static String publicKeyStr = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwdyf35NcDrYXNds5/TqCokLlBCVKpD/HBYT0yednUa"
            + "aUP6fOAC8cbp5FOanqr2CmnyqVyUvuOeGwXgiIMGqXn8OAYAoN2rUMuyDIjIQEK8zln3zJCrBaaJDi639+k711KFwxAvTRljJEH"
            + "BgR11bbAdUuEj5E7ouHWiXdXUgt/7PV9ZaRhVyKvZX7RU42Y6T8kePGQtyjbq48Ss3BNUj861PAg3oavAF2/8xvRnnCYrNKgSnb"
            + "uxHkaFjvt/PjnUUcqAnaAJ1GI5dHUGqNbyXi+Z9nyYrz+Hn15K9G7X5YaO/wKHgAqb0H1UqkdlB5ine9Dyu63rLbD6HmFpEaji"
            + "yM4wIDAQAB";

    public static void main(String[] args) {
        System.out.println(new Date());
    }


    public static String getSignature(String json) {
        String signature = null;
        String md5Str = Md5Util.build(json);
        PublicKey publicKey = null;
        try {
            publicKey = RSAUtil.string2PublicKey(publicKeyStr);
            // 公钥加密/私钥解密
            byte[] publicEncryBytes =  RSAUtil.publicEncrytype(md5Str.getBytes(), publicKey);
            signature = Base64.getEncoder().encodeToString(publicEncryBytes);
            System.out.println("signature is:"+signature);
            return signature;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "1111";

        }
    }


}
