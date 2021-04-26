package com.example.demo.entitys;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "paises")
@Builder
@Data
@AllArgsConstructor @NoArgsConstructor
public class Pais implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "pais_id")
	private Long paisId;	
	
	@Column(name = "nombre",unique = true)
	@NotEmpty(message = "El valor del campo nombre no puede ser vacio")
	private String nombre;
	private static final long serialVersionUID = -4245499977814899810L;

}
