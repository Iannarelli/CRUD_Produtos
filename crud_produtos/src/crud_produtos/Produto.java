package crud_produtos;

public class Produto {

	private String codigo, nome, descricao, tipo;
	private float preco;
	
	private Produto(String codigo, String nome, String descricao, String tipo, float preco) {
		setCodigo(codigo);
		setNome(nome);
		setDescricao(descricao);
		setTipo(tipo);
		setPreco(preco);
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public float getPreco() {
		return preco;
	}

	public void setPreco(float preco) {
		this.preco = preco;
	}
}
