package com.example.demo.repository;

import com.example.demo.model.Reservas; // Importe Reservas (com 's')
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ReservasRepository extends JpaRepository<Reservas, Integer> { // Use Reservas aqui
    // Método para encontrar reservas por ID de usuário (útil para a interface do usuário)
    List<Reservas> findByUsuarioId(int usuarioId); // Retorna List<Reservas>

    // Método para encontrar reservas por ID de quadra (se precisar)
    List<Reservas> findByQuadraId(int quadraId); // Retorna List<Reservas>
    
    Long countByQuadraIdAndDataAndHoraInicio(int quadraId, LocalDate data, LocalTime horaInicio);

 
}