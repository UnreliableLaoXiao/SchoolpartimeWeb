package com.example.schoolparttime.entity;

public class RequestWork {

    long id;

    long user_id;

    long work_id;

    int statu;

    public RequestWork(long id, long user_id, long work_id, int statu) {
        this.id = id;
        this.user_id = user_id;
        this.work_id = work_id;
        this.statu = statu;
    }

    public RequestWork() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public long getWork_id() {
        return work_id;
    }

    public void setWork_id(long work_id) {
        this.work_id = work_id;
    }

    public int getStatu() {
        return statu;
    }

    public void setStatu(int statu) {
        this.statu = statu;
    }
}
