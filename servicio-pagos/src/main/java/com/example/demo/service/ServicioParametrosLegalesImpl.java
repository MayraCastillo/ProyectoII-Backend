package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import com.example.demo.dao.ParametrosLegalesDAO;
import com.example.demo.entity.ParametroLegal;

/**
 * Implementacion de los servicios disponibles sobre los parametros legales
 * 
 * @author Ruben
 *
 */

@Service
@RequiredArgsConstructor
public class ServicioParametrosLegalesImpl implements ServicioParametrosLegales{
	
	/**
	 * Abstraccion del medio de almacenamiento de datos, de las entidades mapeadas en la base de datos
	 * Spring boot, mediante inyeccion de dependencias, logra simplificar la comunicacion con
	 * la base de datos
	 */
	private final ParametrosLegalesDAO miRepositorioParametrosLegales;
	
	
	
	public List<ParametroLegal> listarParametrosLegales() {
		return miRepositorioParametrosLegales.findAll();
	}

	@Override
	public ParametroLegal buscarParametroPorId(Long pParametroId) {
		if(pParametroId != null) {
			return this.miRepositorioParametrosLegales.findById(pParametroId).orElse(null);
		}
		return null;
	}

	@Override
	public ParametroLegal buscarParametroPorNombre(String pNombre) {
		List<ParametroLegal> parametros = null;
		
		if(pNombre != null) {
			parametros = this.miRepositorioParametrosLegales.findByNombreIgnoreCase(pNombre); 
			if(parametros != null) {
				if(parametros.size() > 0) {
					return parametros.get(0);
				}
			}
		}
		return null;
	}

	@Override
	public ParametroLegal agregarParametro(ParametroLegal pParametro) {
		if(pParametro == null) {
			return null;
		}
		if(pParametro.getNombre() == null || pParametro.getValor() == null) {
			return null;
		}
		if(this.miRepositorioParametrosLegales.findByNombreIgnoreCase(pParametro.getNombre()).size() > 0) {
			return null;
		}
			
		ParametroLegal nuevoParametro = ParametroLegal.builder()
				.nombre(pParametro.getNombre().toUpperCase()) // CONVERTIR A MAYUSCULAS
				.valor(pParametro.getValor())
				.build();
		
		return this.miRepositorioParametrosLegales.save(nuevoParametro);
	}

	@Override
	public ParametroLegal actualizarParametro(ParametroLegal pParametro) {
		if(pParametro == null) {
			return null;
		}
		
		// Id con el cual se va a modificar 
		Long parId = pParametro.getParametroId();
		
		if(parId == null) {
			return null;
		}
		
		ParametroLegal parametroEncontrado = this.miRepositorioParametrosLegales.findById(parId).orElse(null);
		
		// Actualizar valores
		if(parametroEncontrado != null) {
			if(pParametro.getNombre() != null)
				if(!pParametro.getNombre().isEmpty())
					parametroEncontrado.setNombre(pParametro.getNombre().toUpperCase());
			
			if(pParametro.getValor() != null)
				parametroEncontrado.setValor(pParametro.getValor());
			
			// Guardar nuevos valores
			return this.miRepositorioParametrosLegales.save(parametroEncontrado);			
		}		
		
		return parametroEncontrado;
	}

}
