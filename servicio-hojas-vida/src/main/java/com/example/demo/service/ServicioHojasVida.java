package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.HojaVida;
import com.example.demo.model.DatosHojaVida;

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
	 * Retorna una hoja de vida por su id, osea por su numero de identificacion
	 * 	
	 * @param pHojaVidaId numero de identificacion de la persona dueña de la hoja de vida
	 * @return Hoja de vida, en caso de ser encontrada
	 */
	public HojaVida buscarHojaVidaPorId(Long pHojaVidaId);
	
	
	/**
	 * Nueva hoja de vida del sistema
	 * 
	 * @param pHojaVida Hoja de vida que se va a registrar
	 * @return Retorna la misma hoja de vida, solo en caso de que se pueda agregar correctamente
	 */
	public HojaVida registrarHojaVida(DatosHojaVida pHojaVida);
	
	
	
	
}
