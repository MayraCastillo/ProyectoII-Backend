package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.demo.DAO.IempleadoDAO;
import com.example.demo.DAO.ImunicipioDAO;
import com.example.demo.entitys.Empleado;
import com.example.demo.entitys.Municipio;

@DataJpaTest
public class EmpleadoRepositoryMockTest {

	@Autowired
	IempleadoDAO empleadoDao;

	@Autowired
	ImunicipioDAO municipioDao;

	@Autowired
	EntityManager entityManager;

	private Empleado crearEmpleado() {
		Municipio municipio = municipioDao.findByNombreIgnoreCase("popayan");
		Empleado empleado = Empleado.builder().numeroDocumento(1L).municipio(municipio).nombres("Hector")
				.apellidos("Meneses").fechaNacimiento(new Date()).tipoDocumento("Cedula").telefono("8231390")
				.direccion("Cra 21").estado("ACTIVO").correo("hecto@unicauca.edu.co").build();
		return empleado;
	}

	@Test
	public void whenCreatedEmpleado_thenReturnEmpleado() {
		
		int tamanioInicial = empleadoDao.findAll().size();
		empleadoDao.save(crearEmpleado());
		int TamanioFinal = empleadoDao.findAll().size();
		entityManager.flush();
		assertThat(tamanioInicial).isNotEqualTo(TamanioFinal);
	}
	
	@Test
	public void whenFindEmpleadoByDocumento_thenReturnEmpleado() {
		empleadoDao.save(crearEmpleado());
		entityManager.flush();
		Empleado empleado = empleadoDao.findByNumeroDocumento(1L);
		assertThat(empleado.getNombres()).isEqualTo("Hector");
	}
	
	@Test
	public void whenFindEmpleadosByEstado_thenReturnListEmpleados() 
	{
		empleadoDao.save(crearEmpleado());
		entityManager.flush();
		List<Empleado> empleados = empleadoDao.findByEstadoIgnoreCase("ACTIVO");
		assertThat(empleados.size()).isEqualTo(2);
	}
	
	@Test
	public void whenFindEmpleados_thenReturnAllEmpleados() 
	{
		empleadoDao.save(crearEmpleado());
		entityManager.flush();
		List<Empleado> empleados = empleadoDao.findAll();
		assertThat(empleados.size()).isEqualTo(3);
	}
	
	@Test
	public void whenFindEmpleadoByNombre_thenListEmpleados() {
		empleadoDao.save(crearEmpleado());
		entityManager.flush();
		List<Empleado> empleados = empleadoDao.findByNombres("Hector");
		assertThat(empleados.size()).isEqualTo(2);
	}

}
