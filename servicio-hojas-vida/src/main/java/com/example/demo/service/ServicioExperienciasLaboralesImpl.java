package com.example.demo.service;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DAO.ExperienciasLaboralesDAO;
import com.example.demo.entity.EmpresaExterna;
import com.example.demo.entity.ExperienciaLaboral;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioExperienciasLaboralesImpl implements IntServicioExperienciasLaborales {

	/**
	 * Referencia a los servicios disponibles sobre las empresas externas, 
	 * utilizando inyeccion de dependencias, automatizado por el framework con la decoracion @Autowired
	 */
	@Autowired
    private IntServicioEmpresasExternas miServicioEmpresasExternas;	
	
	
	/**
	 * Abstraccion del medio de almacenamiento de datos, de las entidades mapeadas en la base de datos
	 * Spring boot, mediante inyeccion de dependencias, logra simplificar la comunicacion con
	 * la base de datos
	 */
	@Autowired
	private ExperienciasLaboralesDAO miRepositorioExperienciasLaborales;
	
	@Override
	public void guardarExperienciasLaborales(@Valid List<ExperienciaLaboral> experiencias) {
		//List<ExperienciaLaboral> experiencias = pHojaVida.getExperienciasLaborales();
		
		if(experiencias != null) {			
			for(ExperienciaLaboral exp : experiencias) {				
				// Guardar experiencia laboral 				
				try {
					EmpresaExterna ee = miServicioEmpresasExternas.crearEmpresaExterna(exp.getEmpresaExterna());
					exp.setEmpresaExterna(ee);
					miRepositorioExperienciasLaborales.save(exp);
				}
				catch(Exception e) {
					System.out.println("Error al agregar experiencia laboral:  " + e.getMessage());
				}
			}
		}
	}

	@Override
	public void eliminarExperienciaLaboral(Long pExperienciaId) {
		if(pExperienciaId != null) {
			this.miRepositorioExperienciasLaborales.deleteById(pExperienciaId);
		}
	}

	@Override
	public void eliminarFaltantes(List<ExperienciaLaboral> experiencias, Long pNumeroDocumento) {
		// Estudios almacenados al momento
		List<ExperienciaLaboral> experienciasExistentes;
		experienciasExistentes = this.miRepositorioExperienciasLaborales.findByNumeroDocumento(pNumeroDocumento);
		
		for(ExperienciaLaboral exp : experienciasExistentes) {
			if( ! this.existeExperienciaLaboral(experiencias, exp)) {
				System.out.println("Eliminando Experiencia Laboral que no se envió, con id --> " + exp.getExpId());
				this.eliminarExperienciaLaboral(exp.getExpId());
			}
		}
	}
	
	@Override
	public void agregarEntidades(List<ExperienciaLaboral> experiencias) {
		if(experiencias != null) {
			for(ExperienciaLaboral exp : experiencias) {
				EmpresaExterna ee = miServicioEmpresasExternas.crearEmpresaExterna(exp.getEmpresaExterna());
				exp.setEmpresaExterna(ee);
			}
		}		
	}
	
	
	private boolean existeExperienciaLaboral(List<ExperienciaLaboral> experiencias, ExperienciaLaboral experienciaLaboral) {
		if(experiencias == null || experienciaLaboral == null) {
			return false;
		}
		
		if(experienciaLaboral.getExpId() != null) {
			for(ExperienciaLaboral exp : experiencias) {
				if(exp.getExpId() != null) {
					if(experienciaLaboral.getExpId().equals(exp.getExpId())) {
						return true;
					}
				}
			}
		}
		return false;
	}


	
}
