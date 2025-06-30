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
	 // Endpoint para criar uma nova reserva
	    @PostMapping("/criar")
	    public ResponseEntity<?> createReserva(@RequestBody Reservas reserva) { // Mudei para ResponseEntity<?> para poder retornar mensagens de erro
	        try {
	            // --- Validação do usuário (como você já tinha) ---
	            if (reserva.getUsuario() == null || reserva.getUsuario().getId() == null) {
	                return new ResponseEntity<>("ID do usuário não fornecido.", HttpStatus.BAD_REQUEST);
	            }
	            Optional<Usuario> usuarioOpt = usuarioRepository.findById(reserva.getUsuario().getId());
	            if (usuarioOpt.isEmpty()) {
	                return new ResponseEntity<>("Usuário não encontrado.", HttpStatus.NOT_FOUND);
	            }

	            // --- Lógica da Quadra (MODIFICADA) ---
	            if (reserva.getQuadra() == null || reserva.getQuadra().getId() == 0) {
	                return new ResponseEntity<>("ID da quadra não fornecido.", HttpStatus.BAD_REQUEST);
	            }
	            
	            // 1. BUSCA a quadra para poder alterar o contador
	            Optional<Quadras> quadraOpt = quadrasRepository.findById(reserva.getQuadra().getId());
	            if (quadraOpt.isEmpty()) {
	                return new ResponseEntity<>("Quadra não encontrada.", HttpStatus.NOT_FOUND);
	            }
	            
	            Quadras quadra = quadraOpt.get();

	            // 2. VERIFICA se ainda há vagas
	            if (quadra.getPessoasAtuais() >= quadra.getLimitePessoas()) {
	                // Retorna um erro específico (409 Conflict) se a quadra estiver lotada
	                return new ResponseEntity<>("Vagas esgotadas para este horário.", HttpStatus.CONFLICT);
	            }

	            // 3. INCREMENTA o contador de pessoas
	            quadra.setPessoasAtuais(quadra.getPessoasAtuais() + 1);
	            quadrasRepository.save(quadra); // 4. SALVA a quadra com o novo valor

	            // 5. CRIA a reserva
	            reserva.setUsuario(usuarioOpt.get());
	            reserva.setQuadra(quadra); // Usa a quadra que acabamos de atualizar

	            Reservas savedReserva = reservasRepository.save(reserva);
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
	 // Endpoint para deletar uma reserva (CANCELAR)
	    @DeleteMapping("/deletar/{id}")
	    public ResponseEntity<HttpStatus> deleteReserva(@PathVariable int id) {
	        try {
	            // 1. ACHA a reserva que será cancelada, em vez de só deletar direto
	            Optional<Reservas> reservaOpt = reservasRepository.findById(id);
	            if (reservaOpt.isEmpty()) {
	                // Se não encontrar a reserva, retorna um erro 404
	                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	            }

	            Reservas reserva = reservaOpt.get();
	            Quadras quadra = reserva.getQuadra();

	            // 2. DECREMENTA o contador na quadra associada
	            if (quadra != null && quadra.getPessoasAtuais() > 0) {
	                quadra.setPessoasAtuais(quadra.getPessoasAtuais() - 1);
	                quadrasRepository.save(quadra); // 3. SALVA a quadra com o contador atualizado
	            }
	            
	            // 4. AGORA SIM, deleta a reserva
	            reservasRepository.delete(reserva);

	            // Retorna sucesso (NO_CONTENT significa que a operação foi bem sucedida mas não há conteúdo para retornar)
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

	        } catch (Exception e) {
	            System.err.println("Erro ao deletar reserva com ID " + id + ": " + e.getMessage());
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	    }
	}