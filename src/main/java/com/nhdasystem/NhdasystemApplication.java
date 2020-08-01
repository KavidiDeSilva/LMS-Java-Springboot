package com.nhdasystem;

import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;




@SpringBootApplication
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class NhdasystemApplication  {

	public static void main(String[] args) {
		SpringApplication.run(NhdasystemApplication.class, args);
	}
}


