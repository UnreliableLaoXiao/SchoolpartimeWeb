package com.example.schoolparttime.filter;

import com.example.schoolparttime.security.aes.AESUtil;
import com.example.schoolparttime.security.md5.Md5Util;
import com.example.schoolparttime.security.rsa.RSAUtil;

import javax.crypto.SecretKey;
import java.security.PrivateKey;
import java.util.Base64;

public class RequestFilter {

    static String privateKeyStr = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDB3J/fk1wOthc12zn9OoKiQuUE"
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

    public static void filterRequest(String aesKey,String data,String signature,String token,FilterResult filter) throws Exception {
        PrivateKey privateKey = RSAUtil.string2Privatekey(privateKeyStr);
        byte[] privateDecryBytes = RSAUtil.privateDecrypt(Base64.getDecoder().decode(aesKey), privateKey);
        String AESKeySecurity_now = new String(privateDecryBytes);
        SecretKey secretKey1 = AESUtil.strKey2SecretKey(AESKeySecurity_now);
        byte[] privateDecryBytes_signature = RSAUtil.privateDecrypt(Base64.getDecoder().decode(signature), privateKey);
        String body_json = new String(AESUtil.decryptAES(Base64.getDecoder().decode(data), secretKey1), "utf-8");
        String md5_old = new String(privateDecryBytes_signature);
        String md5_now = Md5Util.build(body_json);

        long time = Long.valueOf(token);
        System.out.println("time = " + (System.currentTimeMillis() - time));
        if(System.currentTimeMillis() - time < 100000){
            if(md5_now.equals(md5_old)) {
                filter.success(body_json);
            }else {
                filter.fail();
            }
        }else {
            filter.timeout();
        }


    }

    public interface FilterResult{
        void success(String str);
        void fail();
        void timeout();
    }

}
