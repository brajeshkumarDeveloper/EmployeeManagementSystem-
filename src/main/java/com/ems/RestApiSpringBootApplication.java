package com.ems;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;


@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "emplyee", version = "2.0",description="employye data"))
public class RestApiSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestApiSpringBootApplication.class, args);
	}

}
