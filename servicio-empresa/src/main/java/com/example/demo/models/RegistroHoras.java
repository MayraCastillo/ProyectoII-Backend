package com.example.demo.models;

import com.example.demo.entitys.Contrato;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistroHoras {
	private Long registroHorasId;
	private Contrato contrato;
	private Integer horasTrabajadas;

}
