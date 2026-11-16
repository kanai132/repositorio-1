package demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import demo.model.Pedido;
import demo.model.PedidoRepository;

@Service
public class PedidoService {

	private final PedidoRepository pedidoRepository;

	PedidoService(PedidoRepository pedidoRepository) {
		this.pedidoRepository = pedidoRepository;
	}

	public Iterable<Pedido> getAll() {
		return pedidoRepository.findAll();
	}

	public Pedido save(Pedido a) {
		return pedidoRepository.save(a);
	}

	public Optional<Pedido> findById(Integer id) {
		return pedidoRepository.findById(id);
	}

	public void deleteById(Integer id) {
		pedidoRepository.deleteById(id);
	}

}
