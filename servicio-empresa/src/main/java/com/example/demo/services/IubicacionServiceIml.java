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
		Pais pais = paisDAO.findByNombreIgnoreCase(pPais.getNombre());
		if (pais != null) {
			return pais;
		}
		return paisDAO.save(pPais);
	}

	@Override
	@Transactional
	public Departamento crearDepartamento(Departamento pDepartamento) {
		Departamento departamento = departamentoDAO.findByNombreIgnoreCase(pDepartamento.getNombre());
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
		Municipio municipio = municipioDAO.validarExistenciaMunicipio(pMunicipio.getCodigo(), pMunicipio.getDepartamento().getDepartamentoId());
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
	public Departamento buscarDepartamento(Long pIdDepartamento) {

		return departamentoDAO.findById(pIdDepartamento).orElse(null);
	}

	@Override
	public List<Departamento> listarDepartamentosPorPais(Long pIdPais) {
		
		return departamentoDAO.listarDepartamentosPorPais(pIdPais);
	}

	@Override
	public List<Municipio> listarMunicipiosPorDepartamento(Long pIdDepartamento) {
		return municipioDAO.listarMunicipiosPorDepartamento(pIdDepartamento);
	}

	@Override
	public Municipio buscarMunicipioPorId(Long pMunicipioId) {
		return municipioDAO.findById(pMunicipioId).orElse(null);
	}


}
