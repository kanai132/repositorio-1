package demo.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import demo.dto.ClienteDTO;
import demo.model.Cliente;
import demo.model.ClienteRepository;
import demo.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Tag(name = "demo-api", description = "API para manter alunos.")
@Log4j2
@RequestMapping(path = "/demo-api") // This means URL's start with /teste-api
@RestController
public class ClienteController {

	private final ClienteService clienteService;

	private final ClienteRepository clienteRepository;

	ClienteController(ClienteService clienteService, ClienteRepository clienteRepository) {
		this.clienteService = clienteService;
		this.clienteRepository = clienteRepository;
	}

	@Operation(summary = "Criar aluno com parametros.", description = "Retorna uma mensagem.")
	@PostMapping(path = "/cliente/param")
	public ResponseEntity<String> createString(@RequestParam String nome) {

		log.info("createString( " + nome +  " )");

		try {
			Cliente a = new Cliente(nome);
			clienteService.save(a);
			
			return new ResponseEntity<>("Cliente criado com sucesso!", HttpStatus.CREATED);
		} catch (Exception e) {
			log.info("Exceção = " + e); 			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Criar cliente com objeto.", description = "Retorna o objeto criado.")
	@PostMapping(path = "/cliente")
	public ResponseEntity<ClienteDTO> create(@Valid @RequestBody ClienteDTO clienteDTO) {

		log.info("create( " + clienteDTO + " )");
		try {
			Cliente a = clienteService.save(new Cliente(clienteDTO.getNome()));
			
			return new ResponseEntity<>(ClienteDTO.from(a), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Atualizar aluno com objeto.", description = "Retorna uma mensagem. O Id deve existir.")
	@PutMapping("/cliente/{id}")
	public ResponseEntity<String> update(@Valid @RequestBody ClienteDTO clienteDTO, @PathVariable Integer id) {

		log.info("update( " + clienteDTO + ", Id " + id + " )");

		Optional<Cliente> clienteData = clienteService.findById(id);

		try {
			if (clienteData.isPresent()) {
				Cliente a =  clienteData.get();
				a.setNome(clienteDTO.getNome());
				clienteService.save(a);
				
				return ResponseEntity.ok("Cliente alterado com sucesso!"); 
			} else {
				return new ResponseEntity<>("Cliente não cadastrado!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Ocorreu um erro inexperado!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Exclui um aluno por Id.", description = "Retorna uma mensagem. O Id deve existir.")
	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {

		log.info("delete( Id " + id + " )");

		try {

			Optional<Cliente> clienteData = clienteService.findById(id);

			if (clienteData.isPresent()) {
				clienteService.deleteById(id);
			} else {
				return new ResponseEntity<>("Cliente não cadastrado!", HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok("Cliente excluído com sucesso!");

		} catch (Exception e) {
			return new ResponseEntity<>("Ocorreu um erro inexperado!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Recuperar Clientes.", description = "Retorna uma coleção de Clientes.")
	@GetMapping("/alunos")
	public @ResponseBody Iterable<Cliente> getAll() {

		log.info("getAll()");

		return clienteRepository.findAll();
	}

}
