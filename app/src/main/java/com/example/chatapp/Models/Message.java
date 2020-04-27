package com.example.chatapp.Models;

public class Message {
    private String text;
    private long time;
    private boolean seen;
    private String sender;
    private String receiver;

    public Message(){}
    public Message(String text, long time, boolean seen,String sender,String receiver){
        this.text = text;
        this.time = time;
        this.seen = seen;
        this.sender = sender;
        this.receiver = receiver;
    }
    public String getText() {
        return text;
    }

    public long getTime() {
        return time;
    }

    public boolean isSeen() {
        return seen;
    }

    public String getSender() {
        return sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }
}
