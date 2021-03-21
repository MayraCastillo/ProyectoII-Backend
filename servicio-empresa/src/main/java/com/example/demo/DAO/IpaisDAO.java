package com.example.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.Pais;

@Repository
public interface IpaisDAO extends JpaRepository<Pais, Long> {
	public Pais findByNombre(String pNombre);

}
