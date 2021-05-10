package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.example.demo.DAO.IcontratoDAO;
import com.example.demo.DAO.IempleadoDAO;
import com.example.demo.DAO.IempresaDAO;
import com.example.demo.entitys.Contrato;
import com.example.demo.entitys.Empleado;
import com.example.demo.entitys.Empresa;
import com.example.demo.models.ContratoPk;
import com.example.demo.services.IcontratoService;
import com.example.demo.services.IcontratoServiceImpl;

@SpringBootTest
public class ContratoServiceMockTest {

	@Mock
	private IcontratoDAO contratoDao;

	@Autowired
	private IcontratoService contratoServiceImpl;

	@Autowired
	private IempleadoDAO empleadoDao;

	@Autowired
	private IempresaDAO empresaDao;

	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
		contratoServiceImpl = new IcontratoServiceImpl(empleadoDao, contratoDao);
		List<Contrato> contratos = new ArrayList<>();
		Empleado empleado = empleadoDao.findByNumeroDocumento(123L);
		Empresa empresa = empresaDao.findByNombreIgnoreCase("Restaurante PRO");
		ContratoPk contratoPk = ContratoPk.builder().empleado(empleado).empresa(empresa).fechaInicioContrato(new Date())
				.build();
		Contrato contrato = Contrato.builder().contratoId(1L).contratoPk(contratoPk).fechaFinContrato(new Date())
				.fechaIncioPrueba(new Date()).fechaFinPrueba(new Date()).tipo("prestacion de servicios")
				.estado("ACTIVO").salarioBase(1000000.0).build();
		Long documento = contrato.getContratoPk().getEmpleado().getNumeroDocumento();
		Long nit = contrato.getContratoPk().getEmpresa().getNit();
		contratos.add(contrato);
		Mockito.when(contratoDao.findById(1L)).thenReturn(Optional.of(contrato));
		Mockito.when(contratoDao.VerificarContrato(documento, nit)).thenReturn(contrato);
		Mockito.when(contratoDao.findAll()).thenReturn(contratos);
		Mockito.when(contratoDao.save(contrato)).thenReturn(contrato);
		Mockito.when(contratoDao.listarContratos(nit)).thenReturn(contratos);
	}

	@Test
	public void whenValidGetNit_thenListContratos() {
		Contrato contrato = contratoDao.findById(1L).orElse(null);
		Long nit = contrato.getContratoPk().getEmpresa().getNit();
		List<Contrato> contratos = contratoServiceImpl.listarContratos(nit);
		assertThat(contratos.get(0).getEstado()).isEqualTo("INACTIVO");
	}

	@Test
	public void whenValidCreatedContrato_thenReturnNull() {
		Contrato contrato = contratoDao.findById(1L).orElse(null);
		Contrato contratoEncontrato = contratoServiceImpl.crearContrato(contrato);
		assertThat(contratoEncontrato).isNull();
	}

	@Test
	public void whenValidGetIdContrato_thenReturnContrato() {
		Contrato contrato = contratoServiceImpl.buscarContratoPorId(1L);
		assertThat(contrato.getContratoId()).isEqualTo(1L);
	}
}
