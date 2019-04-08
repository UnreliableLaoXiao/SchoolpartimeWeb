package com.example.schoolparttime.entity;

public class ChatRecord {

    /**
     * 聊天记录列表
     */

    long id;   //持有人
    private String img;   //头像
    private String name;   //聊天对象名字
    private String new_mes;   //最新消息
    private long other_id;   //聊天对象id
    private int state;
    private String rcd_date;
    private int no_read;

    public ChatRecord(long id, String img, String name, String new_mes, long other_id, int state, String rcd_date, int no_read) {
        this.id = id;
        this.img = img;
        this.name = name;
        this.new_mes = new_mes;
        this.other_id = other_id;
        this.state = state;
        this.rcd_date = rcd_date;
        this.no_read = no_read;
    }

    public ChatRecord() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNew_mes() {
        return new_mes;
    }

    public void setNew_mes(String new_mes) {
        this.new_mes = new_mes;
    }

    public long getOther_id() {
        return other_id;
    }

    public void setOther_id(long other_id) {
        this.other_id = other_id;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getRcd_date() {
        return rcd_date;
    }

    public void setRcd_date(String rcd_date) {
        this.rcd_date = rcd_date;
    }

    public int getNo_read() {
        return no_read;
    }

    public void setNo_read(int no_read) {
        this.no_read = no_read;
    }

    @Override
    public String toString() {
        return "ChatRecord{" +
                "id=" + id +
                ", img='" + img + '\'' +
                ", name='" + name + '\'' +
                ", new_mes='" + new_mes + '\'' +
                ", other_id=" + other_id +
                ", state=" + state +
                ", rcd_date='" + rcd_date + '\'' +
                ", no_read=" + no_read +
                '}';
    }
}
