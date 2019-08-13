package java_crud_produtos;

import org.json.JSONObject;
import org.simpleframework.http.Query;

public class Produto implements JsonFormatter {

	private String codigo, nome, descricao, tipo;
	private float preco;
	
	public Produto(String codigo, String nome, String descricao, String tipo, float preco) {
		setCodigo(codigo);
		setNome(nome);
		setDescricao(descricao);
		setTipo(tipo);
		setPreco(preco);
	}
	
	public Produto() {};

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

	public void adicionar(Query query) {
		setCodigo(query.get("codigo"));
		setNome(query.get("nome"));
		setDescricao(query.get("descricao"));
		setPreco(query.getFloat("preco"));
		setTipo(query.get("tipo"));
	}

	@Override
	public JSONObject toJson() {
		JSONObject obj = new JSONObject();
		obj.put("codigo", this.getCodigo());
		obj.put("nome", this.getNome());
		obj.put("descricao", this.getDescricao());
		obj.put("preco", this.getPreco());
		obj.put("tipo", this.getTipo());
		return obj;
	}
}