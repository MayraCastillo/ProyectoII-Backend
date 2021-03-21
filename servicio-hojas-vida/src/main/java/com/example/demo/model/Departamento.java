package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Departamento {
	
	private Long departamentoId;	
	private String nombre;
	
	private Integer codigo;	
	private Pais pais;	

}
