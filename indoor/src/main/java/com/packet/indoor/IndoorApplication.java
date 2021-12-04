package com.packet.indoor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class IndoorApplication {

	public static void main(String[] args) {
		SpringApplication.run(IndoorApplication.class, args);
		System.out.println("Hello World");
	}

}
