package com.example.demo.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entitys.Banco;

@Repository
public interface IbancoDAO extends JpaRepository<Banco, Long> {
	public Banco findByNombre(String pNombre);

}
