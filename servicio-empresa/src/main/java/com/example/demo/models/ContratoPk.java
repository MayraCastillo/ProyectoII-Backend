package com.example.demo.models;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.example.demo.entitys.Empleado;
import com.example.demo.entitys.Empresa;
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
public class ContratoPk implements Serializable {
	private static final long serialVersionUID = 3087859331724741572L;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name ="numero_documento")
	//@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@NotNull(message = "El campo no empleado no puede ser nulo")
	private Empleado empleado;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "nit")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@NotNull(message = "El campo empresa no puede ser vacio")
	private Empresa empresa;
	
	@Column(name = "fecha_inicio_contrato")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	private Date fechaInicioContrato;
}
