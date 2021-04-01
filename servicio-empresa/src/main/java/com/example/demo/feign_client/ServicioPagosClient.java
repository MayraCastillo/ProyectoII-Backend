package com.example.demo.feign_client;

import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "servicio-pagos")
public interface ServicioPagosClient {

}
