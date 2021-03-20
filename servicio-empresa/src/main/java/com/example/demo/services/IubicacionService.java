package com.example.demo.services;

import java.util.List;

import com.example.demo.entitys.Departamento;
import com.example.demo.entitys.Municipio;
import com.example.demo.entitys.Pais;

public interface IubicacionService {
	public Pais crearPais(Pais pPais);
	public Departamento crearDepartamento(Departamento pDepartamento);
	public Municipio crearMunicipio(Municipio pMunicipio);
	public List<Pais> listarPaises();
	public List<Departamento> listarDepartamentos();
	public List<Municipio> listarMunicipios();
	public Departamento buscarDepartamento(Long PIdDepartamento);

}
