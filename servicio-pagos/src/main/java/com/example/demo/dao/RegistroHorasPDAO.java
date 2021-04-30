package com.example.demo.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.RegistroHorasP;

@Repository
public interface RegistroHorasPDAO extends JpaRepository<RegistroHorasP, Long> {

}
