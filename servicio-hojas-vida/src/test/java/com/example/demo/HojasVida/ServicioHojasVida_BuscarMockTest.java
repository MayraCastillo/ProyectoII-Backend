package com.example.demo.HojasVida;

import java.util.Optional;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.DAO.HojasVidaDAO;
import com.example.demo.DTO.HojaVidaDTO;
import com.example.demo.entity.HojaVida;
import com.example.demo.service.ServicioHojasVidaImpl;
import com.example.demo.service.ServicioUbicacionImpl;



@SpringBootTest
public class ServicioHojasVida_BuscarMockTest {
	
	@Mock
	public HojasVidaDAO miRepositorioHojasVida;
	
	@Mock
	public ServicioUbicacionImpl servicioUbicacion; 
	
	@Autowired
	public ServicioHojasVidaImpl servicioHV;
	
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		servicioHV.setMiRepositorioHojasVida(miRepositorioHojasVida);
		servicioHV.setServicioUbicacion(servicioUbicacion);
				
		HojaVida nuevaHV = HojaVida.builder()
				.numeroDocumento(1000L)
				.apellidos("Gomez")
				.calificacion(8.0)
				.correo("lupita@gmail.com")
				.direccion("Carrera 9na")
				//.estadoPersona("PROSPECTO")
				.municipioId(1L)
				//.nitEmpresa("1")
				.nombres("Lupita")
				.telefono("3157856547")
				.tipoDocumento("CEDULA")
				.build();
		
		// Mock del repositorio de hojas de vida
		Mockito.when(this.miRepositorioHojasVida.findById(1000L)).thenReturn(Optional.of(nuevaHV));
		Mockito.when(this.miRepositorioHojasVida.findByCorreo("lupita@gmail.com")).thenReturn(nuevaHV);
		Mockito.when(this.miRepositorioHojasVida.save(nuevaHV)).thenReturn(nuevaHV);
		
		// Mock del servicio de ubicacion
		Mockito.when(this.servicioUbicacion.validarUbicacion(1L)).thenReturn(true);
	}
	
	
	@Test
	public void buscarPorIdValido() {
		HojaVidaDTO hvEncontrada = servicioHV.buscarHojaVidaPorId(1000L);
		Assertions.assertThat(hvEncontrada.getNumeroDocumento()).isEqualTo(1000L);
		Assertions.assertThat(hvEncontrada.getNombres()).isEqualTo("Lupita");
	}
	
	@Test
	public void buscarPorIdInvalido() {
		HojaVidaDTO hvEncontrada = servicioHV.buscarHojaVidaPorId(1001L);
		Assertions.assertThat(hvEncontrada).isNull();
	}
	
	@Test
	public void buscarPorCorreoValido() {
		HojaVidaDTO hvEncontrada = servicioHV.buscarHojaVidaPorCorreo("lupita@gmail.com");
		Assertions.assertThat(hvEncontrada.getCorreo()).isEqualTo("lupita@gmail.com");
		Assertions.assertThat(hvEncontrada.getNombres()).isEqualTo("Lupita");
	}
	
	@Test
	public void buscarPorCorreoInvalido() {
		HojaVidaDTO hvEncontrada = servicioHV.buscarHojaVidaPorCorreo("lupita2@gmail.com");
		Assertions.assertThat(hvEncontrada).isNull();
	}
	

}
