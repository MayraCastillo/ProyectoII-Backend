package com.example.demo.service;

import java.util.List;

import com.example.demo.DTO.HojaVidaDTO;

/**
 * Define los servicios que pueden ser usados por la API
 * 
 * @author Ruben
 *
 */


public interface IntServicioHojasVida {

	
	/**
	 * Retorna todas las hojas de vida del sistema, usar en ambiente de desarrollo
	 * 
	 * @return Lista con todas las hojas de vida registradas en el sistema
	 */
	public List<HojaVidaDTO> listarHojasVida();	
	
	
	/**
	 * Retorna una hoja de vida por su id, osea por su numero de identificacion
	 * 	
	 * @param pHojaVidaId numero de identificacion de la persona dueña de la hoja de vida
	 * @return Hoja de vida, en caso de ser encontrada
	 */
	public HojaVidaDTO buscarHojaVidaPorId(Long pHojaVidaId);
	
	
	/**
	 * Retorna una hoja de vida por su correo, que es unico para cada hv
	 * 	
	 * @param pCorreo correo para buscar hv
	 * @return Hoja de vida, en caso de ser encontrada
	 */
	public HojaVidaDTO buscarHojaVidaPorCorreo(String pCorreo);
	
	
	/**
	 * Nueva hoja de vida del sistema
	 * 
	 * @param pHojaVida Hoja de vida que se va a registrar
	 * @return Retorna la misma hoja de vida, solo en caso de que se pueda agregar correctamente
	 */
	public HojaVidaDTO registrarHojaVida(HojaVidaDTO pHojaVidaDTO);
	
	
	/**
	 * Actualiza la informacion de una hoja de vida, debe traer toda la estructura de la hoja de vida,
	 * con los campos iguales si no se desean cambiar, y diferentes si no, por ejemplo, 
	 * si en la base de datos la hoja de vida tiene 2 referencias familiares y solo se pasa una
	 * de ellas con su id, la otra se borra.
	 * 
	 * @param pHojaVida Hoja de vida con todos sus campos
	 * @return Hoja de vida actualizada
	 */
	public HojaVidaDTO actualizarHojaVida(HojaVidaDTO pHojaVidaDTO);
	
	
	
}
