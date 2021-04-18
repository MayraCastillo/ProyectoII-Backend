package com.example.demo;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.demo.DAO.IempleadoDAO;
import com.example.demo.DAO.ImunicipioDAO;
import com.example.demo.DAO.IterceroDAO;
import com.example.demo.DAO.ItipoTerceroDAO;
import com.example.demo.entitys.Empleado;
import com.example.demo.entitys.Municipio;
import com.example.demo.entitys.Tercero;
import com.example.demo.entitys.TipoTercero;

@DataJpaTest
public class TerceroRepositoryMockTest {

	@Autowired
	IterceroDAO terceroDAO;

	@Autowired
	ImunicipioDAO municipioDAO;

	@Autowired
	ItipoTerceroDAO tipoTerceroDao;
	
	@Autowired
	IempleadoDAO empleadoDAO;

	private Tercero crearTercero() {
		Municipio municipio = municipioDAO.findByNombreIgnoreCase("popayan");
		TipoTercero tipoTercero = tipoTerceroDao.findByAbreviacion("EPS");
		Tercero terceroPrueba = Tercero.builder().nit(1234L).nombre("terceroPrueba").municipio(municipio).tipoTercero(tipoTercero)
				.direccion("Cra 21").correo("terceroPrueba@gmail.com").telefono("3134237212").estado("ACTIVO").build();
		return terceroPrueba;
	}

	@Test
	public void whenListTerceros_thenReturnListTerceros() {
		List<Tercero> terceros = terceroDAO.findAll();
		Assertions.assertThat(terceros.size()).isEqualTo(4);
	}

	@Test
	public void whenCreatedTercero_thenReturnTercero() {
		terceroDAO.save(crearTercero());
		List<Tercero> terceros = terceroDAO.findAll();
		Assertions.assertThat(terceros.size()).isEqualTo(5);
	}

	@Test
	public void whenFindTerceroByNombre_thenReturnTercero() {
		Tercero terceroPrueba = terceroDAO.save(crearTercero());
		Tercero tercero = terceroDAO.findByNombre(terceroPrueba.getNombre());
		Assertions.assertThat(tercero).isNotNull();
	}

	@Test
	public void whenFindTercerosByTipo_thenReturnListTerceros() {
		Tercero terceroPrueba = terceroDAO.save(crearTercero());
		List<Tercero> terceros = terceroDAO.listarTercerosPorTipo(terceroPrueba.getTipoTercero().getTipoTerceroId());
		Assertions.assertThat(terceros.size()).isEqualTo(3);
	}
	
	@Test
	public void whenFindTercerosByDocumento_thenReturnListTerceros() 
	{
		Empleado empleado = empleadoDAO.findById(123L).orElse(null);
		List<Tercero> terceros = terceroDAO.listarTercerosPorEmpleado(empleado.getNumeroDocumento());
		Assertions.assertThat(terceros.size()).isEqualTo(2);
	}
}
