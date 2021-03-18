package com.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.ReferenciaFamiliar;


@Repository
public interface ReferenciasFamiliaresDAO  extends JpaRepository<ReferenciaFamiliar, Long>{

}
