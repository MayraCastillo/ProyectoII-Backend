package com.example.demo.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.demo.entity.ParametroLegal;

@Repository
public interface ParametrosLegalesDAO extends JpaRepository<ParametroLegal, Long>{

	/**
	 * Buscar un parametro legal, por su nombre, deberia ser unico 
	 * pero se retorna una lista para evitar errores
	 * 
	 * @param nombre nombre del parametro a buscar
	 * @return Lista de parametros con ese nombre
	 */
	//public List<ParametroLegal> findByNombreIgnoreCase(String nombre);	
	public ParametroLegal findByNombreIgnoreCase(String nombre);
	
}
