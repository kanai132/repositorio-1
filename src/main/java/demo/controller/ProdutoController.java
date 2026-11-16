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
import demo.dto.ProdutoDTO;
import demo.model.Cliente;
import demo.model.ClienteRepository;
import demo.model.Produto;
import demo.model.ProdutoRepository;
import demo.model.enums.Categoria;
import demo.service.ClienteService;
import demo.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;

@Tag(name = "demo-api", description = "API para manter alunos.")
@Log4j2
@RequestMapping(path = "/demo-api") // This means URL's start with /teste-api
@RestController
public class ProdutoController {

	private final ProdutoService produtoService;

	private final ProdutoRepository produtoRepository;

	ProdutoController(ProdutoService produtoService, ProdutoRepository produtoRepository) {
		this.produtoService = produtoService;
		this.produtoRepository = produtoRepository;
	}

	@Operation(summary = "Criar produto com parametros.", description = "Retorna uma mensagem.")
	@PostMapping(path = "/produto/param")
	public ResponseEntity<String> createString(@RequestParam String nome, Float valor, Categoria categoria) {

		log.info("createString( " + nome +","+ valor +","+categoria+ " )");

		try {
			Produto a = new Produto(nome, valor, categoria);
			produtoService.save(a);
			
			return new ResponseEntity<>("produto criado com sucesso!", HttpStatus.CREATED);
		} catch (Exception e) {
			log.info("Exceção = " + e); 			
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Criar produto com objeto.", description = "Retorna o objeto criado.")
	@PostMapping(path = "/produto")
	public ResponseEntity<ProdutoDTO> create(@Valid @RequestBody ProdutoDTO produtoDTO) {

		log.info("create( " + produtoDTO + " )");
		try {
			Produto a = produtoService.save(new Produto(produtoDTO.getNome(), produtoDTO.getValor(), produtoDTO.getCategoria()));
			
			return new ResponseEntity<>(ProdutoDTO.from(a), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Atualizar produto com objeto.", description = "Retorna uma mensagem. O Id deve existir.")
	@PutMapping("/produto/{id}")
	public ResponseEntity<String> update(@Valid @RequestBody ProdutoDTO produtoDTO, @PathVariable Integer id) {

		log.info("update( " + produtoDTO + ", Id " + id + " )");

		Optional<Produto> produtoNome = produtoService.findById(id);
		

		try {
			if (produtoNome.isPresent()) {
				Produto a =  produtoNome.get();
				a.setNome(produtoDTO.getNome());
				a.setValor(produtoDTO.getValor());
				a.setCategoria(produtoDTO.getCategoria());
				produtoService.save(a);
				
				return ResponseEntity.ok("Produto alterado com sucesso!"); 
			} else {
				return new ResponseEntity<>("Produto não cadastrado!", HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			return new ResponseEntity<>("Ocorreu um erro inexperado!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Exclui um produto por Id.", description = "Retorna uma mensagem. O Id deve existir.")
	@DeleteMapping("/produto/{id}")
	public ResponseEntity<String> delete(@PathVariable Integer id) {

		log.info("delete( Id " + id + " )");

		try {

			Optional<Produto> produtoNome = produtoService.findById(id);

			if (produtoNome.isPresent()) {
				produtoService.deleteById(id);
			} else {
				return new ResponseEntity<>("Produto não cadastrado!", HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok("Produto excluído com sucesso!");

		} catch (Exception e) {
			return new ResponseEntity<>("Ocorreu um erro inexperado!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Recuperar produto.", description = "Retorna uma coleção de produtos.")
	@GetMapping("/produtos")
	public @ResponseBody Iterable<Produto> getAll() {

		log.info("getAll()");

		return produtoRepository.findAll();
	}

}
