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
	 * Buscar hoja de vida por correo, el correo es validado con unique (unico)
	 * 
	 * @param correo cadena con el correo a buscar
	 * @return Hoja de vida encontrada
	 */
	public HojaVida findByCorreo(String correo);
	
}
