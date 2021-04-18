package com.example.demo.service;

import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.DAO.ReferenciasPersonalesDAO;
import com.example.demo.entity.ReferenciaPersonal;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServicioReferenciasPersonalesImpl implements IntServicioReferenciasPersonales{

	/**
	 * Abstraccion del medio de almacenamiento de datos, de las entidades mapeadas en la base de datos
	 * Spring boot, mediante inyeccion de dependencias, logra simplificar la comunicacion con
	 * la base de datos
	 */
	@Autowired
	private ReferenciasPersonalesDAO miRepositorioReferenciasPersonales;
	
	@Override
	public void guardarReferenciasPersonales(@Valid List<ReferenciaPersonal> referencias) {
		//referencias = pHojaVida.getReferenciasPersonales();		
		if(referencias != null) {			
			for(ReferenciaPersonal ref : referencias) {				
				// Guardar referencia 				
				try {
					miRepositorioReferenciasPersonales.save(ref);
				}
				catch(Exception e) {
					System.out.println("Error al agregar referencia personal:  " + e.getMessage());
				}
			}
		}
	}

	@Override
	public void eliminarReferenciaPersonal(Long pReferenciaId) {
		if(pReferenciaId != null) {
			this.miRepositorioReferenciasPersonales.deleteById(pReferenciaId);
		}
	}

	@Override
	public void eliminarFaltantes(List<ReferenciaPersonal> referencias, Long pNumeroDocumento) {
		// Referencias almacenadas al momento
		List<ReferenciaPersonal> referenciasExistentes;
		referenciasExistentes = this.miRepositorioReferenciasPersonales.findByNumeroDocumento(pNumeroDocumento);
		
		for(ReferenciaPersonal ref : referenciasExistentes) {
			if( ! this.existeReferencia(referencias, ref)) {
				System.out.println("Eliminando referencia personal que no se envió, con id --> " + ref.getReferenciaId());
				this.eliminarReferenciaPersonal(ref.getReferenciaId());
			}
		}
	}
	
	
	
	
	private boolean existeReferencia(List<ReferenciaPersonal> referencias, ReferenciaPersonal pReferencia) {
		if(referencias == null || pReferencia == null) {
			return false;
		}
		
		if(pReferencia.getReferenciaId() != null) {
			for(ReferenciaPersonal ref : referencias) {
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
