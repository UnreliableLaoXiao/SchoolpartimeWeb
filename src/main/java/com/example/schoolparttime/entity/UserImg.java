package com.example.schoolparttime.entity;

public class UserImg {

    private int id;
    private long userid;
    private int type;
    private String url;

    public UserImg(int id, long userid, int type, String url) {
        this.id = id;
        this.userid = userid;
        this.type = type;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public String toString() {
        return "UserImg{" +
                "id=" + id +
                ", userid=" + userid +
                ", type=" + type +
                ", url='" + url + '\'' +
                '}';
    }

    public UserImg() {
    }
}
