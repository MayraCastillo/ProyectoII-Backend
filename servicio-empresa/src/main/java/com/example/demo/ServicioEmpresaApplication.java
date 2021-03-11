package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableEurekaClient
@EnableFeignClients
public class ServicioEmpresaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServicioEmpresaApplication.class, args);
	}

}
