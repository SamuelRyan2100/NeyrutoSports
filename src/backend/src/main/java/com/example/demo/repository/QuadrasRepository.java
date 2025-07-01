package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.model.Quadras;

import jakarta.transaction.Transactional;

@Repository
public interface QuadrasRepository extends JpaRepository<Quadras, Integer> {
	Quadras findByNome(String nome);

    @Modifying
    @Transactional
    @Query("UPDATE Quadras q SET q.ativa = false WHERE q.ativa = true")
    int desativarTodasAtivas();
	//List<Quadras> findAll();
}
