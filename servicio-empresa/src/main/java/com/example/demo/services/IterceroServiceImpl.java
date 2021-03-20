package com.example.demo.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.DAO.ImunicipioDAO;
import com.example.demo.DAO.IterceroDAO;
import com.example.demo.DAO.ItipoTerceroDAO;
import com.example.demo.entitys.Municipio;
import com.example.demo.entitys.Tercero;
import com.example.demo.entitys.TipoTercero;

@Service
public class IterceroServiceImpl implements IterceroService {

	@Autowired
	private IterceroDAO terceroDAO;

	@Autowired
	private ImunicipioDAO municipioDAO;
	
	@Autowired
	private ItipoTerceroDAO tipoTerceroDAO;

	@Override
	public Tercero crearTercero(Tercero pTercero) {
		Tercero tercero = terceroDAO.findByNombre(pTercero.getNombre());
		if (tercero != null) {
			return tercero;
		}
		Municipio municipio = municipioDAO.findById(pTercero.getMunicipio().getMunicipio_id()).orElse(null);
		pTercero.setMunicipio(municipio);
		
		TipoTercero tipoTercero = tipoTerceroDAO.findById(pTercero.getTipoTercero().getTipoTerceroId()).orElse(null);
	    pTercero.setTipoTercero(tipoTercero);
		return terceroDAO.save(pTercero);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tercero> listarTerceros() {

		return terceroDAO.findAll();
	}

	@Override
	@Transactional
	public Tercero actualizarTercero(Tercero pTercero) {
		Tercero tercero = terceroDAO.findById(pTercero.getTerceroId()).orElse(null);
		if(tercero == null) 
		{
			return null;
		}
		tercero.setCorreo(pTercero.getCorreo());
		tercero.setNombre(pTercero.getNombre());
		tercero.setTelefono(pTercero.getTelefono());
		tercero.setTipoTercero(pTercero.getTipoTercero());
		return terceroDAO.save(tercero);
	}

}
