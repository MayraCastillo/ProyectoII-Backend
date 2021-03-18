package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.HojaVida;

/**
 * Define los servicios que pueden ser usados por la API
 * 
 * @author Ruben
 *
 */


public interface ServicioHojasVida {

	
	/**
	 * Retorna todas las hojas de vida del sistema, usar en ambiente de desarrollo
	 * 
	 * @return Lista con todas las hojas de vida registradas en el sistema
	 */
	public List<HojaVida> listarHojasVida();	
	
	
	/**
	 * Nueva hoja de vida del sistema
	 * 
	 * @param pHojaVida Hoja de vida que se va a registrar
	 * @return Retorna la misma hoja de vida, solo en caso de que se pueda agregar correctamente
	 */
	public HojaVida registrarHojaVida(HojaVida pHojaVida);
	
	
	
	
}
