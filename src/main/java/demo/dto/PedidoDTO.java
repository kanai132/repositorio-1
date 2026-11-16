package demo.dto;

import java.time.LocalDate;

import demo.model.Cliente;
import demo.model.Pedido;
import demo.model.enums.Status;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PedidoDTO {

	private Integer id;
	
	@NotNull(message = "A data do pedido é obrigatório!")
	private LocalDate data;
	
    @NotNull(message = "O cliente é obrigatório!")
    private Integer clienteId;
    private Status status;

    public PedidoDTO(Integer id2, LocalDate data2, Integer id3, Status status2) {
		this.id = id2;
		this.data = data2;
		this.clienteId = id3;
		this.status = status2;
	}

	public static PedidoDTO from(Pedido pedido ) {
		return new PedidoDTO(pedido.getId(), pedido.getData(), pedido.getCliente().getId(), pedido.getStatus() );
	}

	
}
