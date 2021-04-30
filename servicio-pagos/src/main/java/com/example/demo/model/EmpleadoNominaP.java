package com.example.demo.model;

import java.io.Serializable;

import com.example.demo.entity.FactoresNoSalariales;
import com.example.demo.entity.FactoresSalariales;
import com.example.demo.entity.NominaP;
import com.example.demo.entity.RegistroHorasP;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoNominaP implements Serializable {
	
	
	private Double salarioBase;
	private Long contratoId;
	private RegistroHorasP registroHoras;
	private FactoresSalariales factoresSalariales;
	private FactoresNoSalariales factoresNoSalariales;
	private NominaP nomina;
	
	private static final long serialVersionUID = 9158594240464130885L;
}
