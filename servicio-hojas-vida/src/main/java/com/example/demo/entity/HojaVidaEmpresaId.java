package com.example.demo.entity;

import java.io.Serializable;

import lombok.Data;

@Data
public class HojaVidaEmpresaId implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long numeroDocumento;	
	private String nitEmpresa;
	
	public HojaVidaEmpresaId(Long numeroDocumento, String nitEmpresa) {
		this.numeroDocumento = numeroDocumento;
		this.nitEmpresa = nitEmpresa;
	}

}
