package com.cch.securitydemo01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@EnableGlobalMethodSecurity(securedEnabled = true,prePostEnabled = true)
public class Securitydemo01Application {

	public static void main(String[] args) {
		SpringApplication.run(Securitydemo01Application.class, args);
	}

}
