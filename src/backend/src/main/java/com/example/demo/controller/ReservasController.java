package com.example.demo.controller;

import com.example.demo.model.Reservas; // Importe Reservas (com 's' no final)
import com.example.demo.model.Usuario;
import com.example.demo.model.Quadras;
import com.example.demo.repository.ReservasRepository; // Importe ReservasRepository
import com.example.demo.repository.UsuarioRepository;
import com.example.demo.repository.QuadrasRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/reservas")
@CrossOrigin(origins = "*")
public class ReservasController {

    @Autowired
    private ReservasRepository reservasRepository; // Use ReservasRepository

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private QuadrasRepository quadrasRepository;

    // Endpoint para criar uma nova reserva
    @PostMapping("/criar")
    public ResponseEntity<Reservas> createReserva(@RequestBody Reservas reserva) { // Use Reservas
        try {
            if (reserva.getUsuario() == null || reserva.getUsuario().getId() == null) {
                 return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(reserva.getUsuario().getId());
            if (usuarioOpt.isEmpty()) {
                System.err.println("Erro ao criar reserva: Usuário com ID " + reserva.getUsuario().getId() + " não encontrado.");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            if (reserva.getQuadra() == null || reserva.getQuadra().getId() == 0) {
                 return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            Optional<Quadras> quadraOpt = quadrasRepository.findById(reserva.getQuadra().getId());
            if (quadraOpt.isEmpty()) {
                System.err.println("Erro ao criar reserva: Quadra com ID " + reserva.getQuadra().getId() + " não encontrada.");
                return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
            }

            reserva.setUsuario(usuarioOpt.get());
            reserva.setQuadra(quadraOpt.get());

            Reservas savedReserva = reservasRepository.save(reserva); // Use reservasRepository e Reservas
            return new ResponseEntity<>(savedReserva, HttpStatus.CREATED);
        } catch (Exception e) {
            System.err.println("Erro ao criar reserva: " + e.getMessage());
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint para obter todas as reservas
    @GetMapping("/todas")
    public ResponseEntity<List<Reservas>> getAllReservas() { // Use List<Reservas>
        List<Reservas> reservas = reservasRepository.findAll(); // Use reservasRepository
        return ResponseEntity.ok(reservas);
    }

    // Endpoint para obter reservas por ID de usuário
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Reservas>> getReservasByUsuarioId(@PathVariable int usuarioId) { // Use List<Reservas>
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        List<Reservas> reservas = reservasRepository.findByUsuarioId(usuarioId); // Use List<Reservas>
        return ResponseEntity.ok(reservas);
    }

    // Endpoint para deletar uma reserva
    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<HttpStatus> deleteReserva(@PathVariable int id) {
        try {
            reservasRepository.deleteById(id); // Use reservasRepository
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            System.err.println("Erro ao deletar reserva com ID " + id + ": " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}