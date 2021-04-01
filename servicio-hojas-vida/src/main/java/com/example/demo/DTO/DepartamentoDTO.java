package com.example.demo.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartamentoDTO {
	
	private Long departamentoId;	
	private String nombre;
	
	private Integer codigo;	
	private PaisDTO pais;	

}
