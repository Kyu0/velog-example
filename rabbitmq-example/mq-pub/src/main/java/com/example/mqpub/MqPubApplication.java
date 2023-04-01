package com.example.mqpub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class MqPubApplication {

	public static void main(String[] args) {
		SpringApplication.run(MqPubApplication.class, args);
	}

}
