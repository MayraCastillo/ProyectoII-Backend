package com.example.demo.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping
@RequestMapping (value = "/tercero")
public class TerceroController {

	
	@GetMapping(value = "/listar")
	public List<String> listar(){
		System.out.print("/nhola/n");
		return null;
	}

}
