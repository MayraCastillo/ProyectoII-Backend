package com.example.demo.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Pais {

	private Long paisId;	
	private String nombre;	
	
}
