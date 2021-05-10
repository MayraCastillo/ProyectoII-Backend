package com.example.demo.model;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Nomina implements Serializable{

	private Date fechaInicio;
	private Date fechaFin;
	private String detalle;
	private static final long serialVersionUID = 7615641315575601498L;
}
