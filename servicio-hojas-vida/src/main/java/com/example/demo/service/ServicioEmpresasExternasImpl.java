package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.EmpresaExterna;
import com.example.demo.repository.EmpresasExternasDAO;

import lombok.RequiredArgsConstructor;

/**
 * Implementacion de las operaciones sobre las empresas externas
 * 
 * @author Ruben
 *
 */


@Service
@RequiredArgsConstructor
public class ServicioEmpresasExternasImpl implements ServicioEmpresasExternas{
	
	/**
	 * Abstraccion del medio de almacenamiento de datos, de las entidades mapeadas en la base de datos
	 * Spring boot, mediante inyeccion de dependencias, logra simplificar la comunicacion con
	 * la base de datos
	 */
	private final EmpresasExternasDAO miRepositorioEmpresasExternas;

	@Override
	public List<EmpresaExterna> listarEmpresasExternas() {
		return miRepositorioEmpresasExternas.findAll();
	}

	@Override
	public EmpresaExterna crearEmpresaExterna(EmpresaExterna pEmpresa) {
		EmpresaExterna nuevaEmpresa = null;
		
		// si se proporciona id y existe el registro, no se crea
		if(pEmpresa != null) {
			if(pEmpresa.getEmpresaExternaId() != null) {
				nuevaEmpresa = this.miRepositorioEmpresasExternas.findById(pEmpresa.getEmpresaExternaId()).orElse(null);
			}
		}
		
		if(nuevaEmpresa == null) {			
			// Crear nueva empresa externa
			nuevaEmpresa = EmpresaExterna.builder()
					.nombre(pEmpresa.getNombre())
					.contacto(pEmpresa.getContacto())
					.telefono(pEmpresa.getTelefono()).build();
			
			return this.miRepositorioEmpresasExternas.save(nuevaEmpresa);						
		}
		
		return nuevaEmpresa;
	}
	
}
