package com.example.user.kavproject;

import com.example.user.kavproject.interfaces.Request;

public class RequestImpl implements Request {

    private int timeDelay;
    private String descr;

    public RequestImpl(int time, String descr) {
        this.timeDelay = time;
        this.descr = descr;
    }

    @Override
    public int getTimeDelay() {
        return timeDelay;
    }

    @Override
    public String getDescr() {
        if (descr == null)
            return "null";
        return descr;
    }
}
