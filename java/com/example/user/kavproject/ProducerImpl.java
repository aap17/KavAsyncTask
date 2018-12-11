package com.example.user.kavproject;

import com.example.user.kavproject.interfaces.Producer;
import com.example.user.kavproject.interfaces.Request;
import com.example.user.kavproject.interfaces.Stopper;

import java.util.Random;
import java.util.Timer;
import java.util.concurrent.ThreadLocalRandom;

public class ProducerImpl implements Producer {
    public ProducerImpl() {
    }

    @Override
    public Request getRequest(Stopper stopSignal) {
        return new RequestImpl(ThreadLocalRandom.current().nextInt(3, 4 + 1), "requestId " + ThreadLocalRandom.current().nextInt(0, 100));
    }
}
