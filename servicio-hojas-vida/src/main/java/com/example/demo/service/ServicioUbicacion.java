package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.demo.DTO.DepartamentoDTO;
import com.example.demo.DTO.HojaVidaDTO;
import com.example.demo.DTO.MunicipioDTO;
import com.example.demo.DTO.PaisDTO;
import com.example.demo.feign_client.EmpresaClient;

import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * Se encarga de la gestion de la ubicacion, que incluye municipios, departamentos, paises
 * y sus distintos formatos de manejo de datos, como DTO o entidades
 * 
 * @author Ruben
 *
 */



@Component
@Data
@NoArgsConstructor
public class ServicioUbicacion {
	
	/**
	 * Referencia al servicio de la empresa, utilizando inyeccion de dependencias
	 * automatizado por el framework 
	 */
	@Autowired	
	private EmpresaClient clienteEmpresa;
	
	
	/**
	 * Almacena los municipios diferentes encontrados en cada hoja de vida, para no pedirlos 
	 * siempre al otro microservicio 
	 */
	//@Autowired
	//private List<MunicipioDTO> cacheMunicipios;
	
	
	
	
	
	public boolean validarUbicacion(Long pIdMunicipio) {
		boolean esValido = false;
		
		if(pIdMunicipio != null) {
			MunicipioDTO municipioEncontrado = this.consultarMunicipio(pIdMunicipio);
			if(municipioEncontrado != null) {
				esValido = true;
			}
		}
		
		return esValido;
	}
	
	public MunicipioDTO consultarMunicipio(Long pIdMunicipio) {
		if(pIdMunicipio != null) {
			
			MunicipioDTO municipioEncontrado = null;			
			try {
				municipioEncontrado = this.clienteEmpresa.buscarMunPorId(pIdMunicipio);
				return municipioEncontrado;
			}catch(Exception e){
				System.out.println("Error al buscar el municipio");
			}			
		}
		return null;
	}	
	
	public void agregarUbicacionAHojaVida(HojaVidaDTO pHojaVidaDTO) {
		if(pHojaVidaDTO != null) {
			MunicipioDTO munEncontrado = this.consultarMunicipio(pHojaVidaDTO.getMunicipioId());
			if(munEncontrado != null) {
				this.agregarDatosMunicipioAHojaVida(pHojaVidaDTO, munEncontrado);
			}
		}
	}
	
	public void agregarUbicacionAHojasVida(List<HojaVidaDTO> pHojasVidaDTO){
		// Almacena los municipios diferentes encontrados en cada hoja de vida
		// TODO ¿como variable global?
		List<MunicipioDTO> cacheMunicipios = new ArrayList<MunicipioDTO>();
				
		for(HojaVidaDTO hv: pHojasVidaDTO) {
			//Si el municipio ya se encontro para otra hoja de vida, no se tiene que pedir de nuevo			 			
			for(MunicipioDTO mun: cacheMunicipios) {
				if(hv.getMunicipioId().equals(mun.getMunicipio_id())) {
					this.agregarDatosMunicipioAHojaVida(hv, mun);
					break;
				}
			}
			// no se ha encontrado ese municipio
			if(hv.getMunicipioId() == null || hv.getDepartamentoId() == null || hv.getPaisId() == null){
				MunicipioDTO munEncontrado = this.consultarMunicipio(hv.getMunicipioId());
				
				// Lo agrego a la lista de municipios encontrados
				if(munEncontrado != null) {
					cacheMunicipios.add(munEncontrado);
					this.agregarDatosMunicipioAHojaVida(hv, munEncontrado);
				}
			}
		}
		
	}
	
	
	private void agregarDatosMunicipioAHojaVida(HojaVidaDTO pHojaVidaDTO, MunicipioDTO pMunicipio) {
		if(pHojaVidaDTO != null && pMunicipio != null) {
			pHojaVidaDTO.setMunicipioId(pMunicipio.getMunicipio_id());
			pHojaVidaDTO.setNombreMunicipio(pMunicipio.getNombre());
			
			DepartamentoDTO depto = pMunicipio.getDepartamento();
			if(depto != null) {
				pHojaVidaDTO.setDepartamentoId(depto.getDepartamentoId());
				pHojaVidaDTO.setNombreDepartamento(depto.getNombre());
				
				PaisDTO pais = depto.getPais();
				if(pais != null) {
					pHojaVidaDTO.setPaisId(pais.getPaisId());
					pHojaVidaDTO.setNombrePais(pais.getNombre());
				}
			}
		}
	}
}
