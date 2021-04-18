package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import com.example.demo.DAO.IcontratoDAO;
import com.example.demo.DAO.IempleadoDAO;
import com.example.demo.DAO.IempresaDAO;
import com.example.demo.DAO.ItarifaArlDAO;
import com.example.demo.entitys.Contrato;
import com.example.demo.entitys.Empleado;
import com.example.demo.entitys.Empresa;
import com.example.demo.entitys.TarifaArl;
import com.example.demo.models.ContratoPk;

@DataJpaTest
public class ContratoRepositoryMockTest {
	
	@Autowired
	private IcontratoDAO contratoDao;
	
	@Autowired
	private EntityManager entityManager;
	
	@Autowired
	private IempleadoDAO empleadoDao;
		
	@Autowired
	private IempresaDAO empresaDao;
	
	@Autowired
	private ItarifaArlDAO tarifaArlDao;
	
	public Contrato crearContrato() 
	{
		Empleado empleado = empleadoDao.findByNumeroDocumento(123L);
		Empresa empresa = empresaDao.findByNombreIgnoreCase("Restaurante PRO");
		TarifaArl tarifaArl = tarifaArlDao.findById(1L).orElse(null);
		ContratoPk contratoPk = ContratoPk.builder().empleado(empleado).empresa(empresa).fechaInicioContrato(new Date()).build();
		Contrato contrato = Contrato.builder().contratoPk(contratoPk).tarifaArl(tarifaArl).fechaFinContrato(new Date())
				.fechaIncioPrueba(new Date()).fechaFinPrueba(new Date()).tipo("prestacion de servicios").estado("ACTIVO")
				.salarioBase(1000000.0).build();
		return contrato;
	}
	
	@Test
	public void whenValidVerificacionContrato_thenReturnContrato() {
		Contrato contratoEnviado = crearContrato();
		Long Documento = contratoEnviado.getContratoPk().getEmpleado().getNumeroDocumento();
		Long nit = contratoEnviado.getContratoPk().getEmpresa().getNit();
		Contrato contratoRecuperado = contratoDao.VerificarContrato(Documento, nit);
		assertEquals(contratoEnviado.getEstado(), contratoRecuperado.getEstado());
	}
	
	@Test
	public void whenCreatedContrato_thenReturnContrato(){
		int tamanioInicial = contratoDao.findAll().size();
		contratoDao.save(crearContrato());
		entityManager.flush();
		int tamanioFinal = contratoDao.findAll().size();
		assertThat(tamanioInicial).isNotEqualTo(tamanioFinal);
	}
	
	@Test
	public void whenFindContratorByEmpresa_thenReturnListContratos() 
	{
		Contrato contrato = crearContrato();
		contratoDao.save(contrato);
		entityManager.flush();
		List<Contrato> contratos = contratoDao.listarContratosPorEstado(contrato.getContratoPk().getEmpresa().getNit()); 
	    assertThat(contratos.size()).isEqualTo(3);
	}

}
