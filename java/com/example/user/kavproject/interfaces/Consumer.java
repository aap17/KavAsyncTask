package com.example.user.kavproject.interfaces;

public interface Consumer {

    void processRequest(Request request, Stopper stopSignal);

    void parseQueue(int threadNum);

}
