package com.example.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.HojaVida;

/**
 * DAO que proporciona el acceso a las hojas de vida
 * 
 * @author Ruben
 *
 */


@Repository
public interface HojasVidaDAO extends JpaRepository<HojaVida, Long> {

	/**
	 * Buscar por Id (numero de documento), buscar todo, viene de forma implicita
	 */
	
}
