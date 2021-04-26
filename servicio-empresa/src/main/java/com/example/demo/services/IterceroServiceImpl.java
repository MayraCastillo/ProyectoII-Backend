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

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class IterceroServiceImpl implements IterceroService {

	private final IterceroDAO terceroDAO;

	@Autowired
	private ImunicipioDAO municipioDAO;

	@Autowired
	private ItipoTerceroDAO tipoTerceroDAO;

	@Override
	public Tercero crearTercero(Tercero pTercero) {
		Tercero tercero = terceroDAO.findByNit(pTercero.getNit());
		if (tercero != null) {
			return null;
		}
		Municipio municipio = municipioDAO.findById(pTercero.getMunicipio().getMunicipio_id()).orElse(null);
		pTercero.setMunicipio(municipio);

		TipoTercero tipoTercero = tipoTerceroDAO.findById(pTercero.getTipoTercero().getTipoTerceroId()).orElse(null);
		pTercero.setTipoTercero(tipoTercero);
		pTercero.setEstado("ACTIVO");
		return terceroDAO.save(pTercero);
	}

	@Override
	public TipoTercero crearTipoTercero(TipoTercero pTipoTercero) {
		TipoTercero tipoTercero = tipoTerceroDAO.findByAbreviacion(pTipoTercero.getAbreviacion());
		if (tipoTercero != null) {
			return tipoTercero;
		}
		return tipoTerceroDAO.save(pTipoTercero);
	}

	@Override
	public Tercero buscarTerceroPorNit(Long pNit) {
		return terceroDAO.findByNit(pNit);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Tercero> listarTerceros() {

		return terceroDAO.findAll();
	}

	@Override
	@Transactional
	public Tercero actualizarTercero(Tercero pTercero) {
		Tercero tercero = terceroDAO.findByNit(pTercero.getNit());
		if (tercero == null) {
			return null;
		}
		tercero.setCorreo(pTercero.getCorreo());
		tercero.setNombre(pTercero.getNombre());
		tercero.setTelefono(pTercero.getTelefono());
		tercero.setDireccion(pTercero.getDireccion());
		tercero.setEstado(pTercero.getEstado());
		return terceroDAO.save(tercero);
	}

	@Override
	public List<Tercero> listarTercerosPorTipo(Long parTipoId) {
		return terceroDAO.listarTercerosPorTipo(parTipoId);
	}

	@Override
	public List<Tercero> listarTercerosPorEmpleado(Long pNumeroDocumento) {
		return terceroDAO.listarTercerosPorEmpleado(pNumeroDocumento);
	}

}
