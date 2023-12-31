package com.hashbus.back;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class BackApplication implements WebMvcConfigurer {
	public static void main(String[] args) {
		SpringApplication.run(BackApplication.class, args);
	}

}
