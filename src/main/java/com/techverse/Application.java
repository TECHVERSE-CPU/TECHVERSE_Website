package com.techverse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		LOGGER.info("Application - Main() :: Begning");		
		SpringApplication.run(Application.class, args);
		LOGGER.info("Application - Main() :: Starting");	
	}

}
