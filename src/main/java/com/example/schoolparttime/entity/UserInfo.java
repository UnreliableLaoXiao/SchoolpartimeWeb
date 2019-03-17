package com.example.schoolparttime.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "t_u_info")
public class UserInfo {

    @Id
    private Long id;

    private String username;

    private int userage;

    private String usersex;

    private String address;

    private String phonenumber;

    private int type;

    public UserInfo(Long id, String username, int userage, String usersex, String address, String phonenumber, int type) {
        this.id = id;
        this.username = username;
        this.userage = userage;
        this.usersex = usersex;
        this.address = address;
        this.phonenumber = phonenumber;
        this.type = type;
    }

    public UserInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserage() {
        return userage;
    }

    public void setUserage(int userage) {
        this.userage = userage;
    }

    public String getUsersex() {
        return usersex;
    }

    public void setUsersex(String usersex) {
        this.usersex = usersex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userage=" + userage +
                ", usersex='" + usersex + '\'' +
                ", address='" + address + '\'' +
                ", phonenumber='" + phonenumber + '\'' +
                ", type=" + type +
                '}';
    }
}
