package com.example.demo.entitys;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name = "terceros")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Tercero {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "terc_id")
	private Long terceroId;
	
	@NotNull(message = "El municipio no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	@JoinColumn(name = "mun_id")
	private Municipio municipio;
	
	@Column(name = "nombre",unique = true)
	@NotEmpty
	private String nombre;
	
	@Column(name = "direccion")
	@NotEmpty
	private String direccion;
	
	@NotEmpty
	@Column(name = "correo",unique = true)
	@Email(message = "Formato email incocorrecto")
	private String correo;
	
	@NotEmpty
	@Column(name = "telefono")	
	private String telefono;
	
	@NotNull(message = "El campo tipoTercero no puede ser vacio")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_tercero_id")
	@JsonIgnoreProperties({ "hibernateLazyInitializer", "handler" })
	private TipoTercero tipoTercero;
	
	@JsonIgnore
	@OneToMany(mappedBy = "empleadoTeceroPk.tercero",fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	private List<Empleado_tercero> listaEmp_terceros;

}
