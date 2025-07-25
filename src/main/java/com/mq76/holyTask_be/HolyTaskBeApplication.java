package com.mq76.holyTask_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HolyTaskBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(HolyTaskBeApplication.class, args);
	}

}
