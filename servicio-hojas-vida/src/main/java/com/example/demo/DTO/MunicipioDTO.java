package com.example.demo.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MunicipioDTO {

	private Long municipio_id;	
	private String nombre;
	
	private Integer codigo;	
	private DepartamentoDTO departamento;
	
}
