package com.example.demo.entitys;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import com.example.demo.models.Empleado_tercero_pk;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "empleados_terceros")
public class Empleado_tercero implements Serializable {
	@EmbeddedId
	private Empleado_tercero_pk empleadoTeceroPk;
	
	@Column(name = "fecha_vinculacion")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(iso = ISO.DATE)
	private  Date fechaVinculacion;
	private static final long serialVersionUID = -3960898202856870021L;
	

}
