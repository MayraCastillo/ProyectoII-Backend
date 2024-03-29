package com.example.demo.feign_client;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.DTO.MunicipioDTO;


@FeignClient(name = "servicio-empresa")
public interface EmpresaClient {
	
	@GetMapping(value = "/buscarMunicipioPorId/{pMunicipioId}")
	public MunicipioDTO buscarMunPorId(@PathVariable Long pMunicipioId);
	
	
	@GetMapping(value = "/consultarEstadoEmpleado/{pNumeroDocumento}/{pNit}")
	public String consultarEstadoEmpleado(@PathVariable Long pNumeroDocumento, @PathVariable Long pNit);

}
