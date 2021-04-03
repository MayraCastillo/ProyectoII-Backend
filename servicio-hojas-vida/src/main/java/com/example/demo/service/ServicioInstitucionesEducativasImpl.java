package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.DAO.InstitucionesEducativasDAO;
import com.example.demo.entity.InstitucionEducativa;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor
public class ServicioInstitucionesEducativasImpl implements ServicioInstitucionesEducativas{
	
	/**
	 * Abstraccion del medio de almacenamiento de datos, de las entidades mapeadas en la base de datos
	 * Spring boot, mediante inyeccion de dependencias, logra simplificar la comunicacion con
	 * la base de datos
	 */
	private final InstitucionesEducativasDAO miRepositorioInstitucionesEducativas;
	
	
	@Override
	public List<InstitucionEducativa> listarInstitucionesEducativas() {
		return miRepositorioInstitucionesEducativas.findAll();
	}

	@Override
	public InstitucionEducativa crearInstitucionEducativa(InstitucionEducativa pInstitucion) {
		InstitucionEducativa nuevaInstitucion = null;
		
		// si se proporciona id y existe el registro, no se crea
		if(pInstitucion != null) {
			if(pInstitucion.getInstId() != null) {
				nuevaInstitucion = this.miRepositorioInstitucionesEducativas.findById(pInstitucion.getInstId()).orElse(null);
			}
		}		
		
		if(nuevaInstitucion == null) {
			
			// Verificar si existe institucion con el mismo nombre
			List<InstitucionEducativa> instEncontradas = miRepositorioInstitucionesEducativas.findByNombreIgnoreCase(pInstitucion.getNombre());
			if(instEncontradas != null) {
				if(instEncontradas.size() > 0) {
					return instEncontradas.get(0);
				}
			}
			
			// Crear nueva empresa externa
			nuevaInstitucion = InstitucionEducativa.builder()
					.nombre(pInstitucion.getNombre())
					.build();
			
			return this.miRepositorioInstitucionesEducativas.save(nuevaInstitucion);						
		}
		
		return nuevaInstitucion;
	}

}
