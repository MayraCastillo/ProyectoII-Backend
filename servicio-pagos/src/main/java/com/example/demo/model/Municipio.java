package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Municipio {

	private Long municipio_id;	
	private String nombre;
	
	private Integer codigo;	
	private Departamento departamento;
	
}
