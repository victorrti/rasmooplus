package com.client.ws.rasmooplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableCaching
@EnableWebMvc
public class WsRasmooPlusApplication {

	public static void main(String[] args) {
		SpringApplication.run(WsRasmooPlusApplication.class, args);
	}

}
