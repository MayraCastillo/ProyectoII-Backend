package com.example.demo.service;

import java.util.List;
import javax.validation.Valid;
import com.example.demo.entity.ReferenciaFamiliar;


/**
 * Gestion de las referencias familiares de una hoja de vida
 * 
 * @author Ruben
 *
 */

public interface IntServicioReferenciasFamiliares {

	/**
	 * Guarda una lista de referencias familiares, cada referencia debe estar asociada a una hoja de vida
	 * mediante el id de la hoja de vida (numero de documento).
	 * Si se especifica un id para la referencia, se actualiza el registro, en otro caso, se crea uno nuevo
	 * 
	 * @param referencias define si se eliminan de la base de datos los registros que no esten en la lista
	 * @param referencias Lista de referencias familiares
	 */
	public void guardarReferenciasFamiliares(@Valid List<ReferenciaFamiliar> referencias);
	
	
	/**
	 * Eliminar una referencia familiar de la base de datos
	 * @param pReferenciaId Identificador de la referencia
	 */
	public void eliminarReferenciaFamiliar(Long pReferenciaId);
	
	
	/**
	 * Elimina referencias familiares que estén en la base de datos, pero no esten en el arreglo 
	 * referencias	  
	 * 
	 * @param estudios Las referencias que estén en el arreglo no se eliminan. Es necesario que el 
	 * identificador no sea nulo
	 * @param pNumeroDocumento Identificador de la hoja de vida a la que deben pertenecer los items del arreglo
	 */
	public void eliminarFaltantes(List<ReferenciaFamiliar> referencias, Long pNumeroDocumento);
}
