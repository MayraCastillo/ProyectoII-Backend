package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.EmpresaExterna;

/**
 * Define las operaciones disponibles sobre las empresas externas, que pueden ser asiciadas
 * a experiencias laborales 
 * 
 * @author Ruben
 *
 */

public interface IntServicioEmpresasExternas {

	public List<EmpresaExterna> listarEmpresasExternas();
	
	public EmpresaExterna crearEmpresaExterna(EmpresaExterna pEmpresa);
	
}
