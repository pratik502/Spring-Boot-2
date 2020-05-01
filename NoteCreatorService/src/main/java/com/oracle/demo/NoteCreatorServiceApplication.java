package com.oracle.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class NoteCreatorServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoteCreatorServiceApplication.class, args);
	}

}

/**
 * Missing @EnableFeignClient will give you error that it could not find bean of FilepathProxy
 * So it is required **/
