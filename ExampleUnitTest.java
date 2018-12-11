package com.example.user.kavproject;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testConsumerEmpty() {
        ConsumerImpl impl = new ConsumerImpl();
        impl.parseQueue(2);
    }

    @Test
    public void testConsumerWithData() {
        ConsumerImpl impl = new ConsumerImpl();
        impl.processRequest(new ProducerImpl().getRequest(null), null);
        impl.parseQueue(2);
    }

    @Test
    public void testConsumerWithDataAfterRun() {
        ConsumerImpl impl = new ConsumerImpl();
        impl.processRequest(new ProducerImpl().getRequest(null), null);
        impl.parseQueue(2);
        impl.processRequest(new ProducerImpl().getRequest(null), null);
        impl.processRequest(new ProducerImpl().getRequest(null), null);
        impl.processRequest(new ProducerImpl().getRequest(null), null);

    }
}