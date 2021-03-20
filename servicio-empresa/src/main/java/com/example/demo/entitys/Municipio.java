package com.example.demo.entitys;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "municipios")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Municipio implements Serializable {

	@Id
	@Column(name = "mun_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long municipio_id;

	@NotNull(message = "El departamento no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "dep_id")
	private Departamento departamento;

	@Column(name = "codigo")
	private Integer codigo;

	@Column(name = "nombre")
	@NotEmpty
	private String nombre;

	private static final long serialVersionUID = -2869925820934122617L;

}
