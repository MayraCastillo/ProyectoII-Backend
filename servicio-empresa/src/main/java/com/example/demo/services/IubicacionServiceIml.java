package com.example.demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DAO.IdepartamentoDAO;
import com.example.demo.DAO.ImunicipioDAO;
import com.example.demo.DAO.IpaisDAO;
import com.example.demo.entitys.Departamento;
import com.example.demo.entitys.Municipio;
import com.example.demo.entitys.Pais;
@Service
public class IubicacionServiceIml implements IubicacionService {

	@Autowired
	private IpaisDAO paisDAO;
	@Autowired
	private IdepartamentoDAO departamentoDAO;

	@Autowired
	private ImunicipioDAO municipioDAO;

	@Override
	@Transactional
	public Pais crearPais(Pais pPais) {
		Pais pais = paisDAO.findByNombre(pPais.getNombre());
		if (pais != null) {
			return pais;
		}
		return paisDAO.save(pPais);
	}

	@Override
	@Transactional
	public Departamento crearDepartamento(Departamento pDepartamento) {
		Departamento departamento = departamentoDAO.findByNombre(pDepartamento.getNombre());
		if (departamento != null) {
			return departamento;
		}
		Pais pais = paisDAO.findById(pDepartamento.getPais().getPaisId()).orElse(null);
		pDepartamento.setPais(pais);
		return departamentoDAO.save(pDepartamento);
	}

	@Override
	@Transactional
	public Municipio crearMunicipio(Municipio pMunicipio) {
		Municipio municipio = municipioDAO.findByNombre(pMunicipio.getNombre());
		if(municipio != null) {
			return municipio;
		}
		Departamento departamento = departamentoDAO.findById(pMunicipio.getDepartamento().getDepartamentoId()).orElse(null);
		pMunicipio.setDepartamento(departamento);
		return municipioDAO.save(pMunicipio);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Pais> listarPaises() {
		return paisDAO.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public List<Departamento> listarDepartamentos() {

		return departamentoDAO.findAll();
	}
	
	@Override
	@Transactional(readOnly = true)
	public List<Municipio> listarMunicipios() {
		return municipioDAO.findAll();
	}


	@Override
	@Transactional(readOnly = true)
	public Departamento buscarDepartamento(Long PIdTercero) {

		return departamentoDAO.findById(PIdTercero).orElse(null);
	}

	@Override
	public List<Departamento> listarDepartamentosPorPais(Long pais) {
		
		return departamentoDAO.listarDepartamentosPorPais(pais);
	}

	@Override
	public List<Municipio> listarMunicipiosPorDepartamento(Long pDepartamento) {
		return municipioDAO.listarMunicipiosPorDepartamento(pDepartamento);
	}


}
