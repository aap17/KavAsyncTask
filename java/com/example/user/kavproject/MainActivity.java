package com.example.user.kavproject;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.user.kavproject.interfaces.Producer;
import com.example.user.kavproject.interfaces.Request;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class MainActivity extends AppCompatActivity {

    private TextView status;
    private ConsumerImpl consumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        status = findViewById(R.id.textView);
        consumer = new ConsumerImpl();
        consumer.parseQueue(2);
    }

    public void refresh(View v) {
        status.setText("заданий в очереди: " + consumer.getQueueSize());
    }

    public void createTask(View v) {
        consumer.processRequest(new ProducerImpl().getRequest(null), null);

    }

    public void stopAll(View v) {
        consumer.stopAll();
    }

}
