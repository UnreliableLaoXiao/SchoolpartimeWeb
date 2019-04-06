package com.example.schoolparttime.entity;

public class Message {
    String mes;

    long from;

    long to;

    int type;

    int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public long getFrom() {
        return from;
    }

    public void setFrom(long from) {
        this.from = from;
    }

    public long getTo() {
        return to;
    }

    public void setTo(long to) {
        this.to = to;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Message() {
    }

    public Message(String mes, long from, long to, int type, int state) {
        this.mes = mes;
        this.from = from;
        this.to = to;
        this.type = type;
        this.state = state;
    }

    @Override
    public String toString() {
        return "Message{" +
                "mes='" + mes + '\'' +
                ", from=" + from +
                ", to=" + to +
                ", type=" + type +
                ", state=" + state +
                '}';
    }
}
