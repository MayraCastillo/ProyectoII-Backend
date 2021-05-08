package com.example.demo.models;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import com.example.demo.entitys.Empleado;
import com.example.demo.entitys.Tercero;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class Empleado_tercero_pk implements Serializable {

	private static final long serialVersionUID = -8915056491685118435L;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nit",insertable = false,updatable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Tercero tercero;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "numero_documento",insertable = false,updatable = false)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private Empleado empleado;

	
}
