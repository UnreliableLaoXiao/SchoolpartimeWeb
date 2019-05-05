package com.example.schoolparttime.entity;


/**
 * 普通用户喜好的兼职类型
 */
public class UserLikeWorkType {
    public UserLikeWorkType(long userId, int likeType1, int likeType2, int likeType3) {
        this.userId = userId;
        this.likeType1 = likeType1;
        this.likeType2 = likeType2;
        this.likeType3 = likeType3;
    }

    private long userId;
    //喜欢类型1
    private int likeType1;

    //喜欢类型2
    private int likeType2;

    //喜欢类型3

    private int likeType3;

    public UserLikeWorkType() {
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public int getLikeType1() {
        return likeType1;
    }

    public void setLikeType1(int likeType1) {
        this.likeType1 = likeType1;
    }

    public int getLikeType2() {
        return likeType2;
    }

    public void setLikeType2(int likeType2) {
        this.likeType2 = likeType2;
    }

    public int getLikeType3() {
        return likeType3;
    }

    public void setLikeType3(int likeType3) {
        this.likeType3 = likeType3;
    }
}