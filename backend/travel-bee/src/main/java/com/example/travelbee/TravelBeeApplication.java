package com.example.travelbee;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class TravelBeeApplication extends SpringBootServletInitializer {

	public static void main(String[] args) { SpringApplication.run(TravelBeeApplication.class, args);}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder){
		return builder.sources(TravelBeeApplication.class);
	}
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/*").allowedOrigins("/*").allowedOrigins("http://ec2-3-15-225-116.us-east-2.compute.amazonaws.com", "http://3.15.225.116", "http://localhost:5173", "http://travel-bee.sytes.net").allowedMethods("*").allowedHeaders("*");
			}
		};

}}
