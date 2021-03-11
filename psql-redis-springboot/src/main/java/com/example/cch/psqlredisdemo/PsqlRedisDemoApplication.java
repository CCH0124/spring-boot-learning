package com.example.cch.psqlredisdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
@EnableJpaAuditing
@SpringBootApplication
@EnableCaching
public class PsqlRedisDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(PsqlRedisDemoApplication.class, args);
	}

}
