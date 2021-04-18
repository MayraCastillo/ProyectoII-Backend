package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.DAO.IterceroDAO;
import com.example.demo.entitys.Tercero;
import com.example.demo.entitys.TipoTercero;
import com.example.demo.services.IterceroService;
import com.example.demo.services.IterceroServiceImpl;

@SpringBootTest
public class TereceroServiceMockTest {

	@Mock
	IterceroDAO terceroDAO;

	@Autowired
	IterceroService terceroService;


	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		terceroService = new IterceroServiceImpl(terceroDAO);
		List<Tercero> tercerosTipo = new ArrayList<>();
		TipoTercero tipoTercero = new TipoTercero();
		tipoTercero.setTipoTerceroId(1L);
		Tercero terceroPrueba = Tercero.builder().nit(1234L).nombre("terceroPrueba").direccion("Cra 21").correo("terceroPrueba@gmail.com").telefono("313423721")
				.estado("ACTIVO").tipoTercero(tipoTercero).build();
		
		tercerosTipo.add(terceroPrueba);
		Mockito.when(terceroDAO.findByNit(1234L)).thenReturn(terceroPrueba);
		Mockito.when(terceroDAO.save(terceroPrueba)).thenReturn(terceroPrueba);
		Mockito.when(terceroDAO.listarTercerosPorTipo(1L)).thenReturn(tercerosTipo);

	}

	@Test
	public void whenValidGetNIT_ThenReturnTercero() {
		Tercero tercero = terceroService.buscarTerceroPorNit(1234L);
		Assertions.assertThat(tercero.getNombre()).isEqualTo("terceroPrueba");
	}

	@Test
	public void whenValidUpdateEstadoTercero_ThenReturnNewEstado() {
		Tercero terceroPrueba = terceroService.buscarTerceroPorNit(1234L);
		terceroPrueba.setEstado("INACTIVO");
		Tercero tercero = terceroService.actualizarTercero(terceroPrueba);
		Assertions.assertThat(tercero.getEstado()).isEqualTo("INACTIVO");
	}

	@Test
	public void whenValidGetIdTipo_ThenReturnListTerceros() {
		List<Tercero> terceros = terceroService.listarTercerosPorTipo(1L);
		assertEquals(terceroDAO.listarTercerosPorTipo(1L), terceros);
	}

}
