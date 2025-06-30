
package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;


import java.util.HashMap;
import java.util.Map;

// Importe sua classe Usuario
// Importe sua interface UsuarioRepository

@RestController
@RequestMapping("/api/usuarios")
@CrossOrigin(origins = "*")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;
  

    // Construtor com injeção de dependência
    public UsuarioController(UsuarioRepository usuarioRepository /*, Opcional: BCryptPasswordEncoder passwordEncoder */) {
        this.usuarioRepository = usuarioRepository;
        // Opcional: this.passwordEncoder = passwordEncoder;
    }

    // Lógica básica para o login
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Usuario usuario) {
        Usuario existente = usuarioRepository.findByEmail(usuario.getEmail());

        if (existente != null && existente.getSenha().equals(usuario.getSenha())) {
       

            // Se o login for bem-sucedido, retorne o ID do usuário e uma mensagem
            Map<String, Object> response = new HashMap<>();
            response.put("id", existente.getId()); // Adiciona o ID do usuário
            response.put("mensagem", "Login bem-sucedido!");
            return ResponseEntity.ok(response); // Status 200 OK

        } else {
            // Se as credenciais forem inválidas
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("mensagem", "Credenciais inválidas.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse); // Status 401 Unauthorized
        }
    }

    // Lógica básica para o cadastro (exemplo para garantir que também retorne o ID)
    @PostMapping("/cadastro")
    public ResponseEntity<Map<String, Object>> cadastrarUsuario(@RequestBody Usuario usuario) {
        // Você deve adicionar validações aqui (email único, senha forte, etc.)

        // Opcional: Codifique a senha antes de salvar (RECOMENDADO)
        // usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));

        Usuario novoUsuario = usuarioRepository.save(usuario); // Assumindo que save retorna a entidade salva

        Map<String, Object> response = new HashMap<>();
        response.put("id", novoUsuario.getId()); // Adiciona o ID do usuário recém-criado
        response.put("mensagem", "Usuário cadastrado com sucesso!");
        return ResponseEntity.status(HttpStatus.CREATED).body(response); // Status 201 Created
    }


}