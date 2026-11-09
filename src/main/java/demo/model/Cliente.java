package demo.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private @Nullable Integer id;

	@NotBlank
	private String nome;
	
	
	public Cliente() {}
	
	public Cliente(String nome) {
		super();
		this.nome = nome;
		
	}

	public Cliente(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
		
	}
	
	
}
