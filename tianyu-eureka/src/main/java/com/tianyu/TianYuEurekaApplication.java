package com.tianyu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 启动类
 * @author Jayce
 * @date 2020-11-27 17:01
 */
@EnableEurekaServer
@SpringBootApplication
public class TianYuEurekaApplication {

	public static void main(String[] args) {
		SpringApplication.run(TianYuEurekaApplication.class, args);
	}

}
