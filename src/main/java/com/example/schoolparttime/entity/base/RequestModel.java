package com.example.schoolparttime.entity.base;

public class RequestModel<T> {
    public String message;
    public T data;
    public String type;
    public int code;

    public RequestModel(String message, T data, String type, int code) {
        this.message = message;
        this.data = data;
        this.type = type;
        this.code = code;
    }

    public RequestModel() {
    }
}
