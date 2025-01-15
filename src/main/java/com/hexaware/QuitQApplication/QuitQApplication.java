package com.hexaware.QuitQApplication;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class QuitQApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuitQApplication.class, args);
	}
	
	@Bean
	public ModelMapper modelMapperBean() {
		return new ModelMapper();
	}

}
