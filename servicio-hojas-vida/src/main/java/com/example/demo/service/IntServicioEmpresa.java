package com.example.demo.service;


/**
 * Contiene la definicion de los servicios generales de la empresa
 * 
 * @author Ruben
 *
 */

public interface IntServicioEmpresa {

	
	/**
	 * Consulta el estado de una persona en una empresa, dependiendo si tiene 
	 * contratos activos o no, o si nunca ha tenido contratos, tendra un estado de 
	 * prospecto
	 * 
	 * @param pNumeroDocumento Numero de identificacion de la persona
	 * @param pNitEmpresa Empresa a la que se requiere consultar el estado del empleado
	 * @return estado : [PROSPECTO, ACTIVO, INACTIVO, PRUEBA]
	 */
	public String consultarEstadoPersona(Long pNumeroDocumento, String pNitEmpresa);
	
	
	
}
