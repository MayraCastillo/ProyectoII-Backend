package com.example.demo.models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import com.example.demo.entitys.Banco;
import com.example.demo.entitys.Empleado;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Empleado_banco_pk implements Serializable{

	private static final long serialVersionUID = -4272417946373769628L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="numero_documento",insertable = false, updatable = false)
	@JsonBackReference
	private Empleado empleado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="banco_id",insertable = false, updatable = false)
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler" })
	private Banco banco;
	
	@Column(name = "numero_cuenta",insertable = false, updatable = false)
	@NotEmpty
	private String numeroCuenta;

}
