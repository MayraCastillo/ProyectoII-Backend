package com.example.demo.model;

import java.io.Serializable;

import com.example.demo.entity.Factores;
import com.example.demo.entity.Pago_nomina;
import com.example.demo.entity.RegistroHoras;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmpleadoNomina implements Serializable {
	private Double salarioBase;
	private Long contratoId;
	private RegistroHoras registroHoras;
	private Factores factoresSalariales;
	private Factores factoresNoSalariales;
	private Pago_nomina pagoNomina;
	private static final long serialVersionUID = -167678057376612961L;
}
