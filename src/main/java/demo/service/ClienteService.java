package demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import demo.model.Cliente;
import demo.model.ClienteRepository;

@Service
public class ClienteService {

	private final ClienteRepository clienteRepository;

	ClienteService(ClienteRepository clienteRepository) {
		this.clienteRepository = clienteRepository;
	}

	public Iterable<Cliente> getAll() {
		return clienteRepository.findAll();
	}

	public Cliente save(Cliente a) {
		return clienteRepository.save(a);
	}

	public Optional<Cliente> findById(Integer id) {
		return clienteRepository.findById(id);
	}

	public void deleteById(Integer id) {
		clienteRepository.deleteById(id);
	}

}
