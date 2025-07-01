package com.payment.taskconsumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@EnableKafka
@SpringBootApplication
public class TaskConsumerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskConsumerApplication.class, args);
	}

}
