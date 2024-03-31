package com.eside.advertisment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class AdvertismentApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdvertismentApplication.class, args);
	}

}
