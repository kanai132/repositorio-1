package demo.model;

import java.time.LocalDate;

import demo.model.enums.Status;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Pedido {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private @Nullable Integer id;
	
	@NotBlank
	private LocalDate data;
	@NotBlank
	@ManyToOne
	private Cliente cliente;
	private Status status;
	
	
	public Pedido() {}
	
	public Pedido(LocalDate data,Cliente cliente,Status status ) {
		super();
		this.data = data;
		this.cliente = cliente;
		this.status = status;
		
	}

	
	
	
}
