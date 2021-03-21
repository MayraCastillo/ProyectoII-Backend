package com.example.demo.services;

import java.util.List;

import com.example.demo.entitys.Tercero;


public interface IterceroService {
	public Tercero crearTercero(Tercero pTercero);
	public List<Tercero> listarTerceros();
	public Tercero actualizarTercero(Tercero pTercero);

}
