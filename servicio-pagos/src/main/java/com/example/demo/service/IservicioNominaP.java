package com.example.demo.service;


import java.util.List;
import com.example.demo.entity.DetalleNomina;
import com.example.demo.entity.FactoresNoSalariales;
import com.example.demo.entity.FactoresSalariales;
import com.example.demo.entity.NominaP;
import com.example.demo.entity.RegistroHorasP;
import com.example.demo.model.EmpleadoNominaP;

public interface IservicioNominaP {
	public DetalleNomina generarDetalleNomina(EmpleadoNominaP pEmpleadoNomina);
	public DetalleNomina guardarDetalleNomina(DetalleNomina pDetalleNomina);
	public List<NominaP>listarPeridoNominas();
	public List<DetalleNomina> listarDetallesNominas();
	public List<FactoresSalariales> listarFactoresSalariales();
	public List<FactoresNoSalariales> listarFactoresNoSalariales();
	public List<RegistroHorasP> ListarRegistroHoras();
	public List<DetalleNomina> listarDetallesNominaPorPeriodo(String pFechaInicio, String pFechaFin);
	public DetalleNomina pagarDetalleNominaEmpleado(Long pContratoId, Long pNominaId);
}
