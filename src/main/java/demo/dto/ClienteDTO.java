package demo.dto;

import demo.model.Cliente;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClienteDTO {

	private Integer id;
	@NotBlank(message = "O nome do produto é obrigatório!")
	private String nome;
	@Email(message = "Email inválido!")
    @NotBlank(message = "O email é obrigatório!")
	


	public ClienteDTO(Integer id, String nome) {
		this.id = id;
		this.nome = nome;
	
	}

	public static ClienteDTO from(Cliente cliente) {
		return new ClienteDTO(cliente.getId(), cliente.getNome() );
	}
}
