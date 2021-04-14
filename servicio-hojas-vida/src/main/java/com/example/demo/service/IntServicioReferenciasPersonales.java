package com.example.demo.service;

import java.util.List;
import javax.validation.Valid;

import com.example.demo.entity.ReferenciaPersonal;


/**
 * Gestion de las referencias personales de una hoja de vida
 * 
 * @author Ruben
 *
 */

public interface IntServicioReferenciasPersonales {

	/**
	 * Guarda una lista de referencias personales, cada referencia debe estar asociada a una hoja de vida
	 * mediante el id de la hoja de vida (numero de documento).
	 * Si se especifica un id para la referencia, se actualiza el registro, en otro caso, se crea uno nuevo
	 * 
	 * @param referencias Lista de referencias personales
	 */
	public void guardarReferenciasPersonales(@Valid List<ReferenciaPersonal> referencias);
	
	
	/**
	 * Eliminar una referencia personal de la base de datos
	 * @param pReferenciaId Identificador de la referencia
	 */
	public void eliminarReferenciaPersonal(Long pReferenciaId);
	
	
	/**
	 * Elimina referencias personales que estén en la base de datos, pero no esten en el arreglo 
	 * referencias	
	 *   
	 * @param estudios Las referencias que estén en el arreglo no se eliminan. Es necesario que el 
	 * identificador no sea nulo
	 * @param pNumeroDocumento Identificador de la hoja de vida a la que deben pertenecer los items del arreglo
	 */
	public void eliminarFaltantes(List<ReferenciaPersonal> referencias, Long pNumeroDocumento);
}
