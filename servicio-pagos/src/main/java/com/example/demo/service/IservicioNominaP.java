package com.example.demo.service;


import java.util.List;
import com.example.demo.entity.DetalleNomina;
import com.example.demo.entity.FactoresNoSalariales;
import com.example.demo.entity.FactoresSalariales;
import com.example.demo.entity.NominaP;
import com.example.demo.entity.RegistroHorasP;
import com.example.demo.entity.SeguridadSocial;
import com.example.demo.model.EmpleadoNominaP;

public interface IservicioNominaP {
	public NominaP crearNomina(NominaP pNomina);
	public DetalleNomina generarDetalleNomina(EmpleadoNominaP pEmpleadoNomina);
	public DetalleNomina guardarDetalleNomina(EmpleadoNominaP empleadoNomina);
	public DetalleNomina pagarDetalleNominaEmpleado(Long pContratoId, Long pNominaId);
	public DetalleNomina buscarDetalleNomina(Long pContratoId, Long pNominaId);
	public List<NominaP>listarPeridoNominas();
	public List<FactoresSalariales> listarFactoresSalariales();
	public List<FactoresNoSalariales> listarFactoresNoSalariales();
	public List<RegistroHorasP> ListarRegistroHoras();
	public List<DetalleNomina> listarDetallesNominas();
	public List<DetalleNomina> listarDetallesNominaPorPeriodo(String pFechaInicio, String pFechaFin);
	public List<DetalleNomina> listarDetallesNominaPorEstado(String pFechaInicio, String pFechaFin, String pEstado);
	public List<DetalleNomina> listarDetallesNominaPorContrato(Long pContratoId);
	public List<DetalleNomina> listarDetNominaPorContratoYestado(Long pContratoID,String pEstado);
	public List<DetalleNomina> listarDetalleNominaPorIdNomina(Long pNominaId);
	public List<DetalleNomina> listDetNominaPorIdNominaYestado(Long pNominaId, String pEstado);
	public List<SeguridadSocial> ListaSeguridadSocial();
}
