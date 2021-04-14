package com.example.demo.service;

import java.util.List;
import javax.validation.Valid;
import com.example.demo.entity.ExperienciaLaboral;


/**
 * Gestion de las experiencias laborales pertenecientes a una hoja de vida
 * @author Ruben
 *
 */

public interface IntServicioExperienciasLaborales {

	/**
	 * Guarda una lista de experiencias laborales, cada experiencia debe estar asociada a una hoja de vida
	 * mediante el id de la hoja de vida (numero de documento).
	 * Si se especifica un id para la experiencia, se actualiza el registro, en otro caso, se crea uno nuevo
	 * 
	 * @param experiencias Lista de experiencias a guardar (entidades)
	 */
	public void guardarExperienciasLaborales(@Valid List<ExperienciaLaboral> experiencias);
	
	
	/**
	 * Eliminar una experiencia de la base de datos
	 * @param pExperienciaId Identificador de la experiencia
	 */
	public void eliminarExperienciaLaboral(Long pExperienciaId);
	
	
	/**
	 * Elimina experiencias que estén en la base de datos, pero no esten en el arreglo 
	 * experiencias	 
	 *  
	 * @param experiencias Las experiencias que estén en el arreglo no se eliminan. Es necesario que el 
	 * identificador no sea nulo
	 * @param pNumeroDocumento Identificador de la hoja de vida a la que deben pertenecer los items del arreglo
	 */
	public void eliminarFaltantes(List<ExperienciaLaboral> experiencias, Long pNumeroDocumento);
	
	
	/**
	 * Para cada entidad de la experiencia, valida si existe en la base de datos, si no es así, la crea	
	 * @param experiencias
	 */
	public void agregarEntidades(List<ExperienciaLaboral> experiencias);
}
