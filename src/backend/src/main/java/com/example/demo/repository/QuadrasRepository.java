package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Quadras;

@Repository
public interface QuadrasRepository extends JpaRepository<Quadras, Integer> {
	Quadras findByNome(String nome);

	//List<Quadras> findAll();
}
