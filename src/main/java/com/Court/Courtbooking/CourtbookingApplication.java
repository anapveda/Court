package com.Court.Courtbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication
@EnableFeignClients
public class CourtbookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CourtbookingApplication.class, args);
	}

}
