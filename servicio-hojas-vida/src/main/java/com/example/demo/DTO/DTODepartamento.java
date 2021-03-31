package com.example.demo.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DTODepartamento {
	
	private Long departamentoId;	
	private String nombre;
	
	private Integer codigo;	
	private DTOPais pais;	

}
