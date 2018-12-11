package com.example.user.kavproject;

import com.example.user.kavproject.interfaces.Consumer;
import com.example.user.kavproject.interfaces.Request;
import com.example.user.kavproject.interfaces.Stopper;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ConsumerImpl implements Consumer {

    Queue<Request> requests;
    ExecutorService executorService;
    ReadWriteLock lock;


    public ConsumerImpl() {
        requests = new LinkedList<Request>();
        executorService = Executors.newFixedThreadPool(10);
        lock = new ReentrantReadWriteLock();
    }

    @Override
    public void processRequest(Request request, Stopper stopSignal) {
        if (stopSignal == null) {
            addTask(request);
        } else {
            stopAll();
        }
    }


    private void addTask(final Request task) {
        Runnable addAsync = new Runnable() {
            @Override
            public void run() {
                lock.writeLock().lock();
                try {
                    requests.add(task);
                } finally {
                    lock.writeLock().unlock();
                }
            }
        };
        new Thread(addAsync).start();
    }

    private Runnable generateRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                while (true) {
                    int timetoSleep = 0;
                    try {
                        lock.writeLock().lock();
                        while (requests.size() > 0) {
                            Request currReq = requests.poll();
                            if (currReq != null) {
                                timetoSleep = currReq.getTimeDelay();
                                System.out.println("processing request: " + currReq.getDescr() + " then sleeping for " + timetoSleep + " seconds");
                            }
                        }
                    } finally {
                        lock.writeLock().unlock();
                        try {
                            TimeUnit.SECONDS.sleep(timetoSleep);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }

                }
            }
        };
    }

    @Override
    public void parseQueue(int threadNum) {
        for (int i = 0; i < threadNum; i++) {
            executorService.submit(generateRunnable());
        }
        if (requests.size() > 0) {
            requests.clear();
        }
        stopAll();
    }

    private void stopService(ExecutorService executor) {
        try {
            System.out.println("attempt to shutdown executor");
            executor.shutdown();
            executor.awaitTermination(30, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            System.err.println("tasks interrupted");
        } finally {
            if (!executor.isTerminated()) {
                System.err.println("cancel non-finished tasks");
            }
            executor.shutdownNow();
            System.out.println("shutdown finished");
        }
    }

    public void stopAll() {
        Thread stopThread = new Thread(new Runnable() {
            @Override
            public void run() {
                stopService(executorService);
            }
        });
        stopThread.start();
        requests.clear();
    }

    public String getQueueSize() {
        lock.readLock().lock();
        String size = String.valueOf(requests.size());
        lock.readLock().unlock();
        return size;
    }

    public void addNewThread() {
        executorService.submit(generateRunnable());
    }


}

