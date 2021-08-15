package com.neueda.assignment.shortenurlapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.neueda.assignment.shortenurlapi.repository")
@SpringBootApplication
public class ShortenUrlApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShortenUrlApiApplication.class, args);
	}

}
