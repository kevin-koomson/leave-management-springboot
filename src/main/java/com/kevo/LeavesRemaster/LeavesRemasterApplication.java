package com.kevo.LeavesRemaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LeavesRemasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeavesRemasterApplication.class, args);
	}

}
