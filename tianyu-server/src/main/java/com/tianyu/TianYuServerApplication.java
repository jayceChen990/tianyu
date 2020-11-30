package com.tianyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 启动类
 *
 * @author admin
 * @date 2020-11-30 10:56
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = "com.tianyu")
public class TianYuServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(TianYuServerApplication.class, args);
	}

}
