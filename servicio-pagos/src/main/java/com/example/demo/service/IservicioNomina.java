package com.example.demo.service;

import java.util.List;

import com.example.demo.entity.Factores;
import com.example.demo.entity.Nomina;
import com.example.demo.entity.Pago_nomina;
import com.example.demo.entity.RegistroHoras;
import com.example.demo.model.EmpleadoNomina;

public interface IservicioNomina {
	public RegistroHoras crearRegistroHoras(RegistroHoras pRegistroHoras);
	public Factores crearFactores(Factores pFactores);
	public Pago_nomina crearPagoNomina(Pago_nomina pPagoNomina);
	public Nomina crearNomina(Nomina pNomina);
	public Nomina CalcularNomina(EmpleadoNomina pEmpleadoNomina);
	public List<Pago_nomina> listarPagosNominas();
	public List<Nomina> listarNominas();
	public List<Factores> listarFactores();
	public List<RegistroHoras> ListarRegistroHoras();
}
