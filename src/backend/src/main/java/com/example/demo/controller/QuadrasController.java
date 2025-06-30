package com.example.demo.controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.example.demo.repository.ReservasRepository;

@RestController
@RequestMapping("api/quadras")
@CrossOrigin(origins = "*")
public class QuadrasController {

    @Autowired
    private QuadrasRepository quadrasRepository;

    @Autowired
    private ReservasRepository reservasRepository;

    @GetMapping("/todasQuadras")
    public ResponseEntity<List<Map<String, Object>>> getAllQuadrasComOcupacao() {
        List<Quadras> quadras = quadrasRepository.findAll();
        List<Map<String, Object>> quadrasComOcupacao = new java.util.ArrayList<>();

        for (Quadras quadra : quadras) {
            Map<String, Object> quadraInfo = new HashMap<>();
            quadraInfo.put("id", quadra.getId());
            quadraInfo.put("nome", quadra.getNome()); // <--- Incluir o nome aqui também para o frontend
            quadraInfo.put("tipo", quadra.getTipo());
            quadraInfo.put("endereco", quadra.getEndereco());
            quadraInfo.put("data", quadra.getData());
            quadraInfo.put("horarioInicio", quadra.getHorarioInicio());
            quadraInfo.put("horarioFim", quadra.getHorarioFim());
            quadraInfo.put("limitePessoas", quadra.getLimitePessoas());

            Long pessoasNaReserva = reservasRepository.countByQuadraIdAndDataAndHoraInicio(
                quadra.getId(), quadra.getData(), quadra.getHorarioInicio()
            );
            quadraInfo.put("pessoasAtuais", pessoasNaReserva != null ? pessoasNaReserva.intValue() : 0);

            quadrasComOcupacao.add(quadraInfo);
        }
        return ResponseEntity.ok(quadrasComOcupacao);
    }

    @GetMapping("/nome/{nome}")
    public ResponseEntity<Quadras> getQuadraByName(@PathVariable String nome){
        Quadras quadra = quadrasRepository.findByNome(nome); // Corrigido para chamar findByNome
        if(quadra != null) {
            return ResponseEntity.ok(quadra);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Map<String, Object>> createQuadra(@RequestBody Quadras quadra){
        try {
            if (quadra.getLimitePessoas() <= 0) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("mensagem", "O limite de pessoas deve ser maior que zero.");
                return ResponseEntity.badRequest().body(errorResponse);
            }

            Quadras savedQuadra = quadrasRepository.save(quadra);
            Map<String, Object> response = new HashMap<>();
            response.put("id", savedQuadra.getId());
            response.put("mensagem", "Quadra/Horário cadastrado com sucesso!");
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch(Exception e) {
            System.err.println("Erro ao cadastrar quadra: " + e.getMessage());
            e.printStackTrace();
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("mensagem", "Erro interno ao cadastrar quadra/horário.");
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}