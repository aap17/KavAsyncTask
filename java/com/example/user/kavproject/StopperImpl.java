package com.example.user.kavproject;

import com.example.user.kavproject.interfaces.Stopper;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class StopperImpl implements Stopper {

    private boolean isForceStop;

    public StopperImpl() {
        isForceStop = ThreadLocalRandom.current().nextBoolean();
    }

    @Override
    public boolean isForceStop() {
        return isForceStop;
    }
}
