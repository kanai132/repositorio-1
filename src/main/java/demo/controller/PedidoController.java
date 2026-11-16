package demo.controller;

import java.time.LocalDate;
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
import demo.dto.PedidoDTO;
import demo.dto.ProdutoDTO;
import demo.model.Cliente;
import demo.model.ClienteRepository;
import demo.model.Pedido;
import demo.model.PedidoRepository;
import demo.model.Produto;
import demo.model.ProdutoRepository;
import demo.model.enums.Categoria;
import demo.model.enums.Status;
import demo.service.ClienteService;
import demo.service.PedidoService;
import demo.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Tag(name = "demo-api", description = "API para manter alunos.")
@Log4j2
@RequestMapping(path = "/demo-api") // This means URL's start with /teste-api
@RestController
public class PedidoController {

	private final PedidoService pedidoService;
	
	private final ClienteService clienteService;

	private final PedidoRepository pedidoRepository;

	PedidoController(PedidoService pedidoService,ClienteService clienteService, PedidoRepository pedidoRepository) {
		this.pedidoService = pedidoService;
		this.clienteService = clienteService;
		this.pedidoRepository = pedidoRepository;
	}

	@Operation(summary = "Criar pedido com parametros.", description = "Retorna uma mensagem.")
	@PostMapping(path = "/pedido/param")
	public ResponseEntity<String> createString(@RequestParam LocalDate data,Cliente cliente,Status status ) {

		log.info("createString( " + data +","+ cliente +","+status+ " )");

		try {
			Pedido a = new Pedido(data, cliente, status);
			pedidoService.save(a);
			
			return new ResponseEntity<>("pedido criado com sucesso!", HttpStatus.CREATED);
		} catch (Exception e) {
			log.info("Exceção = " + e); 			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Criar pedido com objeto.", description = "Retorna o objeto criado.")
	@PostMapping(path = "/pedido")
	public ResponseEntity<PedidoDTO> create(@Valid @RequestBody PedidoDTO pedidoDTO) {

		log.info("create( " + pedidoDTO + " )");
		try {
			Optional<Cliente> cliente = clienteService.findById(pedidoDTO.getClienteId());
			Cliente c = cliente.get();
			Pedido a = pedidoService.save(new Pedido(pedidoDTO.getData(), c, pedidoDTO.getStatus()));
			
			return new ResponseEntity<>(PedidoDTO.from(a), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Atualizar pedido com objeto.", description = "Retorna uma mensagem. O Id deve existir.")
	@PutMapping("/pedido/{id}")
	public ResponseEntity<String> update(@Valid @RequestBody PedidoDTO pedidoDTO, @PathVariable Integer id) {

		log.info("update( " + pedidoDTO + ", Id " + id + " )");

		Optional<Pedido> pedidoData = pedidoService.findById(id);
		

		try {
			if (pedidoData.isPresent()) {
				Pedido a =  pedidoData.get();
				a.setData(pedidoDTO.getData());
				a.setCliente(pedidoDTO.getCliente());
				a.setStatus(pedidoDTO.getStatus());
				pedidoService.save(a);
				
				return ResponseEntity.ok("Pedido alterado com sucesso!"); 
			} else {
				return new ResponseEntity<>("Pedido não cadastrado!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Ocorreu um erro inexperado!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Exclui um pedido por Id.", description = "Retorna uma mensagem. O Id deve existir.")
	@DeleteMapping("/pedido/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {

		log.info("delete( Id " + id + " )");

		try {

			Optional<Pedido> pedidoData = pedidoService.findById(id);

			if (pedidoData.isPresent()) {
				pedidoService.deleteById(id);
			} else {
				return new ResponseEntity<>("Pedido não cadastrado!", HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok("Pedido excluído com sucesso!");

		} catch (Exception e) {
			return new ResponseEntity<>("Ocorreu um erro inexperado!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Recuperar pedido.", description = "Retorna uma coleção de pedidos.")
	@GetMapping("/pedidos")
	public @ResponseBody Iterable<Pedido> getAll() {

		log.info("getAll()");

		return pedidoRepository.findAll();
	}

}
