package demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import demo.model.Produto;
import demo.model.ProdutoRepository;

@Service
public class ProdutoService {

	private final ProdutoRepository produtoRepository;

	ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}

	public Iterable<Produto> getAll() {
		return produtoRepository.findAll();
	}

	public Produto save(Produto a) {
		return produtoRepository.save(a);
	}

	public Optional<Produto> findById(Integer id) {
		return produtoRepository.findById(id);
	}

	public void deleteById(Integer id) {
		produtoRepository.deleteById(id);
	}

}
