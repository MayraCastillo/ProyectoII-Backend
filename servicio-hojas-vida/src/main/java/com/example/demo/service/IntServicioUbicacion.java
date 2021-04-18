package com.example.demo.service;

import java.util.List;

import com.example.demo.DTO.HojaVidaDTO;
import com.example.demo.DTO.MunicipioDTO;

public interface IntServicioUbicacion {

	
	/**
	 * Valida si el Id de un municipio es correcto
	 * 
	 * @param pIdMunicipio ID del municipio a validar
	 * @return es valido o no es valido (true o false)
	 */
	public boolean validarUbicacion(Long pIdMunicipio);
	
	
	/**
	 * Busca un municipio
	 * 
	 * @param pIdMunicipio Id del municipio a buscar
	 * @return Municipio encontrado, null si no lo encuentra
	 */
	public MunicipioDTO consultarMunicipio(Long pIdMunicipio);
	
	
	/**
	 * basado en el atributo municipioId del objeto parametro, se agregan los datos completos de ese municipio, 
	 * departamento y pais
	 * 
	 * @param pHojaVidaDTO Objeto DTO que debe tener el Identificador del municipio
	 */
	public void agregarUbicacionAHojaVida(HojaVidaDTO pHojaVidaDTO);
	
	
	/**
	 * Agrega la informacion de ubicacion a multiples hojas de vida.	 * 
	 * basado en el atributo municipioId de la lista de objetos parametro, se agregan los datos completos de ese municipio, 
	 * departamento y pais
	 * 
	 * @param pHojasVidaDTO Objetos DTO que deben tener el Identificador del municipio
	 */
	public void agregarUbicacionAHojasVida(List<HojaVidaDTO> pHojasVidaDTO);
	
	
}
