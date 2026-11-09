package demo.dto;

import demo.model.Produto;
import demo.model.enums.Categoria;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ProdutoDTO {

	private Integer id;
	@NotBlank(message = "O nome do produto é obrigatório!")
	private String nome;
    @NotBlank(message = "O email é obrigatório!")
    private Float valor;
	private Categoria categoria;


	public ProdutoDTO(Integer id, String nome,Float valor,Categoria categoria) {
		this.id = id;
		this.nome = nome;
		this.valor = valor;
		this.categoria = categoria;
	
	}

	public static ProdutoDTO from(Produto produto) {
		return new ProdutoDTO(produto.getId(), produto.getNome(), produto.getValor(), produto.getCategoria() );
	}
}
