package com.payment.money.adapter.out.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.payment.common.RechargingMoneyTask;
import com.payment.money.application.port.out.SendRecharingMoneyTaskPort;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

@Component
public class TaskProducer implements SendRecharingMoneyTaskPort {
    private final KafkaProducer<String, String> producer;
    private final String topic;
    private final ObjectMapper objectMapper = new ObjectMapper();


    public TaskProducer(
            @Value("${kafka.clusters.bootstrapservers}") String bootStrapServers,
            @Value("${task.topic}") String topic) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootStrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    @Override
    public void sendRechargingMoneyTask(RechargingMoneyTask rechargingMoneyTask) {
        this.sendMessage(rechargingMoneyTask.getTaskID(), rechargingMoneyTask);
    }

    public void sendMessage(String key, RechargingMoneyTask value) {
        String jsonStringToProduce = "";
        try {
            jsonStringToProduce = objectMapper.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, jsonStringToProduce);
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace(); //TODO : 실제 운영시에는 없어져야 하는 부분임
                // 전송 중 예외 발생 시 stderr 혹은 로거로 기록
                System.err.println("LoggingProducer send failed: " + exception.getMessage());
            }
        });
    }
}
