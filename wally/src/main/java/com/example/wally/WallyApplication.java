package com.example.wally;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class WallyApplication {

	public static void main(String[] args) {
		SpringApplication.run(WallyApplication.class, args);
	}

}
