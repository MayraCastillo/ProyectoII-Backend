package com.example.demo.service;
import java.util.List;

import com.example.demo.entity.ParametroLegal;

public interface ServicioParametrosLegales {
	
	public List<ParametroLegal> listarParametrosLegales();
	
	public ParametroLegal buscarParametroPorId(Long pParametroId);
	
	public ParametroLegal buscarParametroPorNombre(String pNombre);
	
	public ParametroLegal agregarParametro(ParametroLegal pParametro);
	
	public ParametroLegal actualizarParametro(ParametroLegal pParametro);

}
