package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.feign_client.EmpresaClient;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Data
public class ServicioEmpresaImpl implements IntServicioEmpresa{

	
	/**
	 * Referencia al servicio de la empresa, utilizando inyeccion de dependencias
	 * automatizado por el framework 
	 */
	@Autowired	
	private EmpresaClient clienteEmpresa;
	
	@Override
	public String consultarEstadoPersona(Long pNumeroDocumento, String pNitEmpresa) {
		return this.consultarEstadoViaMicroservicioEmpresa(pNumeroDocumento, pNitEmpresa);
	}
	
	
	
	
	
	private String consultarEstadoViaMicroservicioEmpresa(Long pNumeroDocumento, String pNitEmpresa) {
		String estado = "ESTADO NO DISPONIBLE";
		Long nitLong = this.convertirStringALong(pNitEmpresa);
		
		if(nitLong != null) {
			try {
				String estadoEncontrado = this.clienteEmpresa.consultarEstadoEmpleado(pNumeroDocumento, nitLong);
				
				// No existe registro de algun contrato de la persona
				if(estadoEncontrado == null) {
					estado = "PROSPECTO";
				}else {
					estado = estadoEncontrado;
				}
			}catch(Exception e) {
				System.out.println("No se pudo conectar con el servicio de empresa para consultar el estado");
			}
		}
		
		return estado;
	}
	
	private Long convertirStringALong(String pValor) {
		Long valorLong = null;
		
		try {
			valorLong = Long.valueOf(pValor);
		}catch(Exception e) {
			System.out.println("No se pudo convertir valor String [" + pValor + "] a Long");
			valorLong = null;
		}
		
		return valorLong;
	}

}
