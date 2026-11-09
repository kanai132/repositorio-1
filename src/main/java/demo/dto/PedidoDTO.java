package demo.dto;

import java.time.LocalDate;

import demo.model.Cliente;
import demo.model.Pedido;
import demo.model.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PedidoDTO {

	
	@NotBlank(message = "A data do pedido é obrigatório!")
	private LocalDate data;
	
    @NotBlank(message = "O cliente é obrigatório!")
    private Cliente cliente;
    private Status status;


	public PedidoDTO(LocalDate data, Cliente cliente,Status status) {
		this.data = data;
		this.cliente = cliente;
		this.status = status;
	
	}

	public static PedidoDTO from(Pedido pedido ) {
		return new PedidoDTO(pedido.getData(), pedido.getCliente(), pedido.getStatus() );
	}
}
