package com.example.demo.service;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DAO.EstudiosDAO;
import com.example.demo.entity.Estudio;
import com.example.demo.entity.InstitucionEducativa;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioEstudiosImpl implements IntServicioEstudios {

	
	/**
	 * Referencia a los servicios disponibles sobre los estudios cursados, 
	 * utilizando inyeccion de dependencias, automatizado por el framework con la decoracion @Autowired
	 */
	@Autowired
    private IntServicioInstitucionesEducativas miServicioInstitucionesEducativas;	
	
	/**
	 * Abstraccion del medio de almacenamiento de datos, de las entidades mapeadas en la base de datos
	 * Spring boot, mediante inyeccion de dependencias, logra simplificar la comunicacion con
	 * la base de datos
	 */
	@Autowired
	private EstudiosDAO miRepositorioEstudios;
	
	
	@Override
	public void guardarEstudios(@Valid List<Estudio> estudios) {
		//List<Estudio> estudios = pHojaVida.getEstudios();		
		if(estudios != null) {			
			for(Estudio est : estudios) {				
				// Guardar estudio				
				try {
					InstitucionEducativa inst = miServicioInstitucionesEducativas.crearInstitucionEducativa(est.getInstitucionEducativa());
					est.setInstitucionEducativa(inst);
					miRepositorioEstudios.save(est);
				}
				catch(Exception e) {
					System.out.println("Error al agregar estudio:  " + e.getMessage());
				}
			}
		}
	}


	@Override
	public void eliminarEstudio(Long pEstudioId) {
		if(pEstudioId != null) {
			this.miRepositorioEstudios.deleteById(pEstudioId);
		}
	}


	@Override
	public void eliminarFaltantes(List<Estudio> estudios, Long pNumeroDocumento) {
		// Estudios almacenados al momento
		List<Estudio> estudiosExistentes;
		estudiosExistentes = this.miRepositorioEstudios.findByNumeroDocumento(pNumeroDocumento);
		
		for(Estudio estudio : estudiosExistentes) {
			if( ! this.existeEstudio(estudios, estudio)) {
				System.out.println("Eliminando estudio que no se envió, con id --> " + estudio.getEstudioId());
				this.eliminarEstudio(estudio.getEstudioId());
			}
		}
	}
	
	@Override
	public void agregarInstituciones(List<Estudio> estudios) {
		if(estudios != null) {
			for(Estudio est : estudios) {
				InstitucionEducativa inst = miServicioInstitucionesEducativas.crearInstitucionEducativa(est.getInstitucionEducativa());
				est.setInstitucionEducativa(inst);
			}
		}	
	}
	
	
	
	
	
	private boolean existeEstudio(List<Estudio> estudios, Estudio estudio) {
		if(estudios == null || estudio == null) {
			return false;
		}
		
		if(estudio.getEstudioId() != null) {
			for(Estudio est : estudios) {
				if(est.getEstudioId() != null) {
					if(estudio.getEstudioId().equals(est.getEstudioId())) {
						return true;
					}
				}
			}
		}
		return false;
	}


	
}
