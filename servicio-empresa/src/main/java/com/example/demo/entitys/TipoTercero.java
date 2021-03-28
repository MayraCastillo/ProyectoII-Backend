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
@Table(name = "tipo_terceros")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TipoTercero implements Serializable {
	
	private static final long serialVersionUID = -2161340611798718210L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "tipo_tercero_id")
	private Long tipoTerceroId;
	
	@Column(name = "nombre",unique = true)
	private String nombre;
	
	@Column(name = "abreviacion",unique = true)
	@NotEmpty
	private String abreviacion;

}
