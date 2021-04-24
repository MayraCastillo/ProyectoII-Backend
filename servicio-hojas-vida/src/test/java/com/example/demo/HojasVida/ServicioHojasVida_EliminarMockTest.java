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
import com.example.demo.service.ServicioHojasVidaImpl;
import com.example.demo.service.ServicioUbicacionImpl;


@SpringBootTest
class ServicioHojasVida_EliminarMockTest {

	@Mock
	public HojasVidaDAO miRepositorioHojasVida;
	
	@Mock
	public ServicioUbicacionImpl servicioUbicacion; 
	
	@Autowired
	public ServicioHojasVidaImpl servicioHV;
	
	
	/**
	 * Aqui agrego la hoja de vida que se va a eliminar posteriormente, tambien 
	 * se configuran los Mock
	 */
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		servicioHV.setMiRepositorioHojasVida(miRepositorioHojasVida);
		servicioHV.setServicioUbicacion(servicioUbicacion);
				
		// Mock del repositorio de hojas de vida
		Mockito.when(this.miRepositorioHojasVida.findById(1000L)).thenReturn(Optional.empty());
		Mockito.when(this.miRepositorioHojasVida.existsById(1000L)).thenReturn(false);		
		
		// Mock del servicio de ubicacion
		Mockito.when(this.servicioUbicacion.validarUbicacion(1L)).thenReturn(true);
		Mockito.when(this.servicioUbicacion.validarUbicacion(99999L)).thenReturn(false);			
	}
	
	
	@Test
	public void eliminarHojaVidaExistente() {
		this.servicioHV.eliminarHojaVida(1000L);
		
		Assertions.assertThat(this.servicioHV.buscarHojaVidaPorId(1000L)).isNull();
	}
	
	@Test
	public void eliminarHojaVidaNoExistente() {
		this.servicioHV.eliminarHojaVida(2000L);
		
		Assertions.assertThat(this.servicioHV.buscarHojaVidaPorId(2000L)).isNull();
	}
	
	

}
