package com.personregister.personregister;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.personregister.personregister")
public class PersonregisterApplication {

	public static void main(String[] args) {

		SpringApplication.run(PersonregisterApplication.class, args);
	}
}
