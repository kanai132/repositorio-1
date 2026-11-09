package demo.aluno.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import demo.model.Cliente;
import demo.model.ClienteRepository;
import demo.service.ClienteService;
import lombok.extern.log4j.Log4j2;

@Log4j2
@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

	public static final Cliente CLIENTE_VALIDO = new Cliente("Murakami");
	public static final Cliente CLIENTE_INVALIDO = new Cliente("" );

	@InjectMocks
	private ClienteService clienteService;

	@Mock
	private ClienteRepository clienteRepository;

	@Test
	void criarCliente_ComDadosValidos_RetornaCliente() {

		when(clienteRepository.save(CLIENTE_VALIDO)).thenReturn(CLIENTE_VALIDO);

		Cliente a = clienteService.save(CLIENTE_VALIDO);

		assertSame(a, CLIENTE_VALIDO);
		assertThat(a).isEqualTo(CLIENTE_VALIDO);
	}

	@Test
	void criarCliente_ComDadosInvalidos_ThrowsException() {

		when(clienteRepository.save(CLIENTE_INVALIDO)).thenThrow(RuntimeException.class);

		assertThatThrownBy(() -> clienteService.save(CLIENTE_INVALIDO)).isInstanceOf(RuntimeException.class);
	}

	@Test
	void excluirCliente_ComIdExistente() {

		assertThatCode(() -> clienteService.deleteById(1));
	}

	@Test
	void excluirAluno_ComIdInexistente_ThrowException() {

		doThrow(new RuntimeException()).when(clienteRepository).deleteById(99);

		assertThatThrownBy(() -> clienteService.deleteById(99)).isInstanceOf(RuntimeException.class);
	}

	@Test
	void chamar_getAll() {

		List<Cliente> clientes = new ArrayList<>();
		clientes.add(CLIENTE_INVALIDO);
		clientes.add(CLIENTE_VALIDO);
		clientes.add(CLIENTE_VALIDO);

		when(clienteRepository.findAll()).thenReturn(clientes);

		List<Cliente> result = (List<Cliente>) clienteService.getAll();
		log.info(result.size());

		assertThat(result).isNotEmpty();
		assertThat(result.size()).isEqualTo(3);
		assertSame(result.size(), 3);
		assertThat(result).hasSize(3);
		assertThat(result.get(0).equals(CLIENTE_INVALIDO));

	}

}
