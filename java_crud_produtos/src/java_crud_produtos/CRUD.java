package java_crud_produtos;

import org.json.JSONArray;
import org.json.JSONObject;
import org.simpleframework.http.Query;

public class CRUD {

	private static ConexaoAPI conexao = new ConexaoAPI();
	
	public String adicionarProduto (Query query) throws Exception {
		JSONArray erros = new JSONArray(new JSONObject(erroPreenchimento(query)).get("erros").toString());
		if(erros.length() > 0)
			return erros.toString();
		else {
			String json_string = "{\"token\":\"" + conexao.getToken()
								+ "\",\"codigo\":\"" + Long.parseLong(query.get("codigo"))
								+ "\",\"nome\":\"" + query.get("nome")
								+ "\",\"preco\":" + query.getFloat("preco")
								+ ",\"tipo\":\"" + query.get("tipo")
								+ "\",\"descricao\":\"" + query.get("descricao")
								+ "\"}";
			String[] retorno = conexao.conectarAPI(conexao.getUrl()+"produto/add", json_string, "POST");
			if (new JSONObject(retorno[2]).get("error").toString().equalsIgnoreCase("false"))
				return "false";
			else
				return "true";
		}
	}
	
	public JSONArray listarProdutos() throws Exception {
		String[] retorno = conexao.conectarAPI(conexao.getUrl()+"produtos/"+conexao.getToken(), "", "GET");
		JSONArray produtos = new JSONArray(new JSONObject(retorno[2]).get("produtos").toString());
		return produtos;
	}

	public void deletarProduto(String codigo) throws Exception {
		conexao.conectarAPI(conexao.getUrl()+"produto/" + codigo + "/delete", "{\"token\":\"" + conexao.getToken() + "\"}", "POST");
	}

	public String alterarProduto (Query query) throws Exception {
		JSONArray erros = new JSONArray(new JSONObject(erroPreenchimento(query)).get("erros").toString());
		if(erros.length() > 0)
			return erros.toString();
		else {
			String json_string = "{\"token\":\"" + conexao.getToken()
								+ "\",\"nome\":\"" + query.get("nome")
								+ "\",\"preco\":" + query.getFloat("preco")
								+ ",\"tipo\":\"" + query.get("tipo")
								+ "\",\"descricao\":\"" + query.get("descricao")
								+ "\"}";
			String[] retorno = conexao.conectarAPI(conexao.getUrl()+"produto/" + Long.parseLong(query.get("codigo")) + "/update",
					json_string, "POST");
			if (new JSONObject(retorno[2]).get("error").toString().equalsIgnoreCase("false"))
				return "false";
			else
				return "true";
		}
	}
	
	public String erroPreenchimento(Query query) {
		StringBuilder erros = new StringBuilder();
		erros.append("{\"erros\":[");
		if(!query.get("codigo").equals("")) {
			Long codigo = Long.parseLong(query.get("codigo"));
			if(codigo <= Long.parseLong("0") || codigo > Long.parseLong("9999999999") || codigo-(Integer.parseInt(codigo.toString())) != 0)
				erros.append("\"codigo\",");
		}
		else
			erros.append("\"codigo\",");
		if(query.get("nome").length() > 15 || query.get("nome").equals(""))
			erros.append("\"nome\",");
		if(!query.get("preco").equals("")) {
			if(query.getFloat("preco") <= 0)
				erros.append("\"preco\",");
		}
		else
			erros.append("\"preco\",");
		if(!query.get("tipo").equalsIgnoreCase("a") && !query.get("tipo").equalsIgnoreCase("b")
				&& !query.get("tipo").equalsIgnoreCase("s") && !query.get("tipo").equalsIgnoreCase("e"))
			erros.append("\"tipo\",");
		if(query.get("descricao").length() > 30)
			erros.append("\"descricao\"");
		if(erros.toString().endsWith(","))
			erros.deleteCharAt(erros.toString().length()-1);
		erros.append("]}");
		return erros.toString();
	}
}