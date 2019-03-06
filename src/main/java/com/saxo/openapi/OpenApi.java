package com.saxo.openapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication(scanBasePackages = { "com.saxo.openapi" })
@ImportResource(locations = { "classpath:spring-mvc.xml" })
public class OpenApi {
	public static void main(String[] args) {
		SpringApplication.run(OpenApi.class, args);
	}

	
}
