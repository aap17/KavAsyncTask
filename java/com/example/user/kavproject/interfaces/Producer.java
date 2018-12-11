package com.example.user.kavproject.interfaces;

public interface Producer {

    Request getRequest(Stopper stopSignal);
}
