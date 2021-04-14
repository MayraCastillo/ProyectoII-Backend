package com.example.demo.service;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DAO.ReferenciasFamiliaresDAO;
import com.example.demo.entity.ReferenciaFamiliar;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioReferenciasFamiliaresImpl implements IntServicioReferenciasFamiliares {

	/**
	 * Abstraccion del medio de almacenamiento de datos, de las entidades mapeadas en la base de datos
	 * Spring boot, mediante inyeccion de dependencias, logra simplificar la comunicacion con
	 * la base de datos
	 */
	@Autowired
	private ReferenciasFamiliaresDAO miRepositorioReferenciasFamiliares;
	
	
	@Override
	public void guardarReferenciasFamiliares(@Valid List<ReferenciaFamiliar> referencias) {		
		if(referencias != null) {			
			for(ReferenciaFamiliar ref : referencias) {
				// Guardar referencia 
				try {
					miRepositorioReferenciasFamiliares.save(ref);
				}
				catch(Exception e) {
					System.out.println("Error al agregar referencia familiar:  " + e.getMessage());
				}
			}
		}
	}


	@Override
	public void eliminarReferenciaFamiliar(Long pReferenciaId) {
		if(pReferenciaId != null) {
			this.miRepositorioReferenciasFamiliares.deleteById(pReferenciaId);
		}
	}


	@Override
	public void eliminarFaltantes(List<ReferenciaFamiliar> referencias, Long pNumeroDocumento) {
		// Referencias almacenadas al momento
		List<ReferenciaFamiliar> referenciasExistentes;
		referenciasExistentes = this.miRepositorioReferenciasFamiliares.findByNumeroDocumento(pNumeroDocumento);
		
		for(ReferenciaFamiliar ref : referenciasExistentes) {
			if( ! this.existeReferencia(referencias, ref)) {
				System.out.println("Eliminando referencia familiar que no se envió, con id --> " + ref.getReferenciaId());
				this.eliminarReferenciaFamiliar(ref.getReferenciaId());
			}
		}
	}
	
	
	
	
	private boolean existeReferencia(List<ReferenciaFamiliar> referencias, ReferenciaFamiliar pReferencia) {
		if(referencias == null || pReferencia == null) {
			return false;
		}
		
		if(pReferencia.getReferenciaId() != null) {
			for(ReferenciaFamiliar ref : referencias) {
				if(ref.getReferenciaId() != null) {
					if(pReferencia.getReferenciaId().equals(ref.getReferenciaId())) {
						return true;
					}
				}
			}
		}
		return false;
	}
	
		
}
