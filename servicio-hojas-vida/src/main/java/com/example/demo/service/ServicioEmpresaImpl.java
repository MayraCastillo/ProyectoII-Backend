package com.example.demo.service;

import org.springframework.stereotype.Service;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Data
public class ServicioEmpresaImpl implements IntServicioEmpresa{

	@Override
	public String consultarEstadoPersona(Long pNumeroDocumento, String pNitEmpresa) {
		String estado = "ESTADO NO DISPONIBLE";
		
		// TODO
		
		return estado;
	}

}
