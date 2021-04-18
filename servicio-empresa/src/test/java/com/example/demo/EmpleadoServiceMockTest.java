package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.DAO.IcontratoDAO;
import com.example.demo.DAO.IempleadoDAO;
import com.example.demo.entitys.Empleado;
import com.example.demo.services.IcontratoService;
import com.example.demo.services.IcontratoServiceImpl;

@SpringBootTest
public class EmpleadoServiceMockTest {

	@Mock
	private IempleadoDAO empleadoDao;
	
	@Autowired
	private IcontratoDAO contratoDao;

	@Autowired
	private IcontratoService contratosServiceImpl;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		contratosServiceImpl = new IcontratoServiceImpl(empleadoDao,contratoDao);
		List<Empleado> empleados = new ArrayList<>();
		Empleado empleadoMock = Empleado.builder().numeroDocumento(1L).nombres("Fabio").apellidos("Meneses")
				.fechaNacimiento(new Date()).tipoDocumento("cedula").telefono("231390").direccion("Cra 21a")
				.estado("ACTIVO").correo("hector@gmail.com").build();
		empleados.add(empleadoMock);
		Mockito.when(empleadoDao.findByNumeroDocumento(1L)).thenReturn(empleadoMock);
		Mockito.when(empleadoDao.save(empleadoMock)).thenReturn(empleadoMock);
		Mockito.when(empleadoDao.findByEstadoIgnoreCase("ACTIVO")).thenReturn(empleados);
	}

	@Test
	public void whenValidGetDocumento_thenReturnEmpleado() {
		Empleado empleado = contratosServiceImpl.buscarEmpleadoPorNumeroDocumento(1L);
		assertThat(empleado.getNombres()).isEqualTo("Fabio");
	}

	@Test
	public void whenValidUpdateEmpleado_thenReturnEmpleado() {
		Empleado empleado = contratosServiceImpl.buscarEmpleadoPorNumeroDocumento(1L);
		empleado.setNombres("Hector");
		empleado.setEstado("INACTIVO");
		Empleado empleadoActualizado = contratosServiceImpl.editarEmpleado(empleado);
		assertThat(empleadoActualizado.getNombres()).isEqualTo("Hector");
		assertThat(empleadoActualizado.getEstado()).isEqualTo("INACTIVO");

	}
	
	@Test
	public void whenValidGetEstadoEmpleado_thenReturnListEmpledos() 
	{
		List<Empleado> empleados = contratosServiceImpl.listarEmpleadosPorEstado("ACTIVO");
		assertEquals(empleadoDao.findByEstadoIgnoreCase("ACTIVO"), empleados);
	}

}
