package com.payment.common.logging.producer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

@Component
public class LoggingProducer {
    private final KafkaProducer<String, String> producer;
    private final String topic;

    // 각 서비스의 환경 변수의 application.yml 파일에서 Kafka 클러스터의 bootstrap servers와 로깅 토픽을 설정합니다.
// 예를 들어, banking-service의 application.yml 파일에 다음과 같이 설정할 수 있습니다:
    public LoggingProducer(
            @Value("${kafka.clusters.bootstrapservers}") String bootstrapServers,
            @Value("${logging.topic}") String topic
    ) {
        // Producer를 초기화 하는 코드임
        Properties props = new Properties();
        //현재는 Broker가 하나이기 때문에 bootstrapServers는 단일 서버의 주소를 사용합니다.
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        // Kafka Producer의 key와 value 직렬화 클래스를 설정합니다. (REDIS와 유사)
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

        this.producer = new KafkaProducer<>(props);
        this.topic = topic;
    }

    /**
     * 키와 메시지를 지정된 로깅 토픽으로 비동기 전송합니다.
     * 이 메서드는 KafkaProducer의 send 메서드를 사용하여 메시지를 전송하며,
     * 전송 완료 후 콜백을 통해 예외를 처리합니다.
     */
    public void sendMessage(String key, String message) {
        ProducerRecord<String, String> record = new ProducerRecord<>(topic, key, message);
        producer.send(record, (metadata, exception) -> {
            if (exception != null) {
                exception.printStackTrace(); // TODO : 실제 운영시에는 없어져야 하는 부분임
                // 전송 중 예외 발생 시 stderr 혹은 로거로 기록
                System.err.println("LoggingProducer send failed: " + exception.getMessage());
            }
        });
    }
}