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
@Table(name = "bancos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Banco implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "banco_id")
	private Long bancoId;
	
	@Column(name = "nombre")
	@NotEmpty
	private String nombre;
	
	private static final long serialVersionUID = -8383539374255988440L;

}
