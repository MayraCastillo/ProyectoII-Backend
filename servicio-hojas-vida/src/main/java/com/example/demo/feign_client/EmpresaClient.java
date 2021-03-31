package com.example.demo.feign_client;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.DTO.DTOMunicipio;


@FeignClient(name = "servicio-empresa")
public interface EmpresaClient {
	
	@GetMapping(value = "/buscarMunicipioPorId/{pMunicipioId}")
	public DTOMunicipio buscarMunPorId(@PathVariable Long pMunicipioId);

}
