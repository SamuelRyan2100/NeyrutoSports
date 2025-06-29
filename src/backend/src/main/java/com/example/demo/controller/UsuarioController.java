
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/cadastro")
    public ResponseEntity<String> cadastrar(@RequestBody Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Usuário já existe.");
        }
        usuarioRepository.save(usuario);
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Usuario usuario) {
        Usuario existente = usuarioRepository.findByEmail(usuario.getEmail());
        if (existente != null && existente.getSenha().equals(usuario.getSenha())) {
            return ResponseEntity.ok("Login bem-sucedido!");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciais inválidas.");
        }
    }
}
