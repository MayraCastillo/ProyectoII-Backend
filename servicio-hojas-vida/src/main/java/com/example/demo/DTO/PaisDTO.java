package com.example.demo.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaisDTO {

	private Long paisId;	
	private String nombre;	
	
}
