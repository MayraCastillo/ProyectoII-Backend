package com.example.demo.services;

import java.util.List;

import com.example.demo.entitys.Tercero;
import com.example.demo.entitys.TipoTercero;


public interface IterceroService {
	public Tercero crearTercero(Tercero pTercero);
	public TipoTercero crearTipoTercero(TipoTercero pTipoTercero);
	public Tercero buscarTerceroPorNit(Long pNit);
	public Tercero actualizarTercero(Tercero pTercero);
	public List<Tercero> listarTerceros();
	public List<Tercero> listarTercerosPorTipo(Long pTipoId);
	public List<Tercero> listarTercerosPorEmpleado(Long pNumeroDocumento);
}
