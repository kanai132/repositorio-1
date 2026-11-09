package demo.model;

import demo.model.enums.Categoria;
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
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private @Nullable Integer id;

	@NotBlank
	private String nome;
	@NotBlank
	private Float valor;
	private Categoria categoria;
	public Produto() {}
	
	public Produto(String nome, Float valor, Categoria categoria) {
		super();
		this.nome = nome;
		this.valor = valor;
		this.categoria = categoria;
	}

	public Produto(Integer id, String nome, Float valor, Categoria categoria) {
		super();
		this.id = id;
		this.nome = nome;
		this.valor = valor;
		this.categoria = categoria;
		
	}
	
	
}
