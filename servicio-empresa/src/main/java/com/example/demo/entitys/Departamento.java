package com.example.demo.entitys;

import java.io.Serializable;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "departamentos")
@Builder
@Data
@AllArgsConstructor @NoArgsConstructor
public class Departamento implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "dep_id")
	private Long departamentoId;

	@NotNull(message = "El pais no puede se vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "pais_id")
	private Pais pais;

	@Column(name = "nombre",unique = true)
	@NotEmpty
	private String nombre;

	@Column(name = "codigo",unique = true)
	@Min(value = 0, message = "El valor del campo codigo no puede ser negativo")
	private Integer codigo;
	
	private static final long serialVersionUID = -926757130904575015L;

}
