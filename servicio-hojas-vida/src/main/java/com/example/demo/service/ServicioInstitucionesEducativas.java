package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.InstitucionEducativa;

/**
 * Define las operaciones disponibles sobre las instituciones educativas, 
 * que pueden ser asiciadas a experiencias laborales 
 * 
 * @author Ruben
 *
 */

public interface ServicioInstitucionesEducativas {

	public List<InstitucionEducativa> listarInstitucionesEducativas();
	
	public InstitucionEducativa crearInstitucionEducativa(InstitucionEducativa pInstitucion);
	
}
