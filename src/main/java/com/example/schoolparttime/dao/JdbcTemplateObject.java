package com.example.schoolparttime.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

/**
 * 功能：设置数据库信息和数据源
 *
 * JdbcTemplat使用
 * 1、导入jar包；2、设置数据库信息；3、设置数据源；4、调用jdbcTemplate对象中的方法实现操作
 */
public class JdbcTemplateObject {

    DriverManagerDataSource dataSource;
    JdbcTemplate jdbcTemplate;

    public JdbcTemplateObject() {
        //        设置数据库信息
        this.dataSource = new DriverManagerDataSource();
        this.dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        this.dataSource.setUrl("jdbc:mysql://localhost:3306/schoolpartime?useUnicode=true&characterEncoding=utf8&serverTimezone=GMT");
        this.dataSource.setUsername("root");
        this.dataSource.setPassword("");

//        设置数据源
        this.jdbcTemplate = new JdbcTemplate(dataSource);

    }

    public DriverManagerDataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DriverManagerDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

}
