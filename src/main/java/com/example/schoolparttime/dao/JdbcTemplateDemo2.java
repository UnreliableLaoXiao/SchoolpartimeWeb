package com.example.schoolparttime.dao;

import com.example.schoolparttime.entity.User;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcTemplateDemo2 {

    // JdbcTemplate使用步骤：
// 1、导入jar包；2、设置数据库信息；3、设置数据源；4、调用jdbcTemplate对象中的方法实现操作

    public static void main(String[] args) {
        // 设置数据库信息和据源
        JdbcTemplateObject jdbcTemplateObject = new JdbcTemplateObject();
        JdbcTemplate jdbcTemplate = jdbcTemplateObject.getJdbcTemplate();

//        插入数据
//        insertData();

//        查询返回某一个值：查询表中数据总数
        queryForOne(jdbcTemplate);

//        查询返回对象
        queryForObject(jdbcTemplate);

//        查询返回list集合
        queryForList(jdbcTemplate);
    }

    //  插入数据
    public static void insertData() {
        JdbcTemplateObject jdbcTemplateObject = new JdbcTemplateObject();
        JdbcTemplate jdbcTemplate = jdbcTemplateObject.getJdbcTemplate();
//        调用jdbcTemplate对象中的方法实现操作
        String sql = "insert into t_user (username,password,verifypsw,type) value(?,?,?,?)";
        int rows = jdbcTemplate.update(sql, "xiaoheidff", "1234", "1234",0);
        System.out.println("插入行数：" + rows);
    }

    /**
     * 查询返回某一个值：查询表中数据总数
     */
    public static void queryForOne(JdbcTemplate jdbcTemplate) {
//        String sql = "select count(*) from user";
//        调用方法获得记录数
//        int count = jdbcTemplate.queryForObject(sql, Integer.class);
//        System.out.println("数据总数：" + count);
    }

    /**
     * 功能：查询返回单个对象
     * 步骤：新建MyRowMapper类实现RowMapper接口，重写mapRow方法，指定返回User对象
     */
    public static void queryForObject(JdbcTemplate jdbcTemplate) {
        String sql = "select * from user where name = ?";
//        新建MyRowMapper类实现RowMapper接口，重写mapRow方法，指定返回User对象
        User user = (User) jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper(User.class), "Tom");
        System.out.println(user);
    }

    /**
     * 功能：查询返回对象集合
     * 步骤：新建MyRowMapper类实现RowMapper接口，重写mapRow方法，指定返回User对象
     */
    public static void queryForList(JdbcTemplate jdbcTemplate) {
        String sql = "select * from user";
//        第三个参数可以省略
        List<User> users = jdbcTemplate.query(sql, new BeanPropertyRowMapper(User.class));
        System.out.println(users);
    }

}
