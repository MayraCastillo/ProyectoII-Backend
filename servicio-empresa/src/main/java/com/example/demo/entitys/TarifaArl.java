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
@Table(name = "tarifas_arl")
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TarifaArl implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "arl_id")
	private Long arlId;
	
	@Column(name = "nivel")
	@NotEmpty
	private String nivel;
	
	@Column(name = "cotizacion")
	private Double cotizacion;
	
	private static final long serialVersionUID = -6915570980828804888L;

}
