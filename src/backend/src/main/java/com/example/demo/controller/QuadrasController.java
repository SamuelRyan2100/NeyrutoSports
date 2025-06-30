package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Quadras;
import com.example.demo.repository.QuadrasRepository;

@RestController
@RequestMapping("api/quadras")
@CrossOrigin(origins = "*")
public class QuadrasController {
	@Autowired
	private QuadrasRepository quadrasRepository;
	
	
	@GetMapping("/todasQuadras")
	public ResponseEntity<List<Quadras>> getAllQuadras(){
		List<Quadras> quadras = quadrasRepository.findAll();
		return ResponseEntity.ok(quadras);
	}
	
	
	@GetMapping("/nome")
	public ResponseEntity<Quadras> getQuadraByName(@PathVariable String nome){
		Quadras quadra = quadrasRepository.findByNome(nome);
		if(quadra != null) {
			return ResponseEntity.ok(quadra);
		}else {
			return ResponseEntity.notFound().build();	
	  }
	}
	
	@PostMapping("/cadastrar")
	public ResponseEntity<Quadras> createQuadra(@RequestBody Quadras quadra){
		try {
			Quadras savedQuadra = quadrasRepository.save(quadra);
			return new ResponseEntity<>(savedQuadra, HttpStatus.CREATED); // <--- Adicionado este RETURN!
		}catch(Exception e) {
            // É bom logar o erro completo para depuração
            System.err.println("Erro ao cadastrar quadra: " + e.getMessage());
            e.printStackTrace(); // Imprime o stack trace completo no console do backend
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	
	}
