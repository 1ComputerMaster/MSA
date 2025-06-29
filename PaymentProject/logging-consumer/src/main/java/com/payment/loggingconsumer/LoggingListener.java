package com.payment.loggingconsumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class LoggingListener {
    @KafkaListener(topics = "${logging.topic}")
    public void onMessage(String msg) {
        System.out.println("▶ " + msg);
    }
}
