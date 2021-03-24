package com.someapp.backend;

import com.someapp.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
public class GimmevibeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(GimmevibeBackendApplication.class, args);
	}

}
