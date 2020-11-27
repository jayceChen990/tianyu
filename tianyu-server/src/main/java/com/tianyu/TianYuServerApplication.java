package com.tianyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.tianyu")
public class TianYuServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TianYuServerApplication.class, args);
	}

}
