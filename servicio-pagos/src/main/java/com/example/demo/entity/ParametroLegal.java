package com.example.demo.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table (name = "parametros_legales")  
@Data
@AllArgsConstructor @NoArgsConstructor @Builder
public class ParametroLegal {
	
	@Id
    @Column(name = "par_id")
    @GeneratedValue (strategy = GenerationType.IDENTITY)
	private Long parametroId;
	
	@NotEmpty(message = "El nombre del parametro no puede ser vacio")
	@Column(name = "nombre", unique = true)
	private String nombre;
	
	@NotNull(message = "El valor del parametro no puede ser nulo")
	@Column(name = "valor")
	@Positive(message = "El valor del parametro no puede ser vacio")
	private Double valor;

}
