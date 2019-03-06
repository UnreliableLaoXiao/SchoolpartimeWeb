package com.example.schoolparttime;

import com.example.schoolparttime.dao.UserRepository;
import com.example.schoolparttime.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SchoolparttimeApplicationTests {

    // 注入userRepository
    @Autowired
    private UserRepository userRepository;

    @Test
    public void contextLoads() {
//        // 添加10个测试用例
//        userRepository.save(new User(1, "小黑", "123454", 123, 1));
//        userRepository.save(new User(2,"小黑1","123454",123,1));
//        userRepository.save(new User(3,"小黑2","123454",123,1));

        // 测试findAll, 查询所有记录
        Assert.assertEquals(4, userRepository.findAll().size());

    }

}
