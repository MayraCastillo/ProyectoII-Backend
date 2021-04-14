package com.example.demo.service;

import java.util.List;
import javax.validation.Valid;
import com.example.demo.entity.Estudio;

/**
 * Gestion de los estudios asociados a una hoja de vida
 * 
 * @author Ruben
 *
 */

public interface IntServicioEstudios {

	
	/**
	 * Guarda una lista de estudios, cada estudio debe estar asociado a una hoja de vida
	 * mediante el id de la hoja de vida (numero de documento).
	 * Si se especifica un id para el estudio, se actualiza el registro, en otro caso, se crea uno nuevo
	 * 
	 * @param estudios Lista de estudios a guardar (entidades)
	 */
	public void guardarEstudios(@Valid List<Estudio> estudios);
	
	
	/**
	 * Eliminar un estudio de la base de datos
	 * @param pEstudioId Identificador de la entidad
	 */
	public void eliminarEstudio(Long pEstudioId);
	
	
	/**
	 * Elimina estudios que estén en la base de datos, pero no esten en el arreglo 
	 * estudios	 * 
	 * 
	 * @param estudios Los estudios que estén en el arreglo no se eliminan. Es necesario que el 
	 * identificador no sea nulo
	 * @param pNumeroDocumento Identificador de la hoja de vida a la que deben pertenecer los items del arreglo
	 */
	public void eliminarFaltantes(List<Estudio> estudios, Long pNumeroDocumento);
	
	
	/**
	 * Para cada institucion del estudio, valida si existe en la base de datos, si no es así, la crea	
	 * @param experiencias
	 */
	public void agregarInstituciones(List<Estudio> estudios);
	
	
}
