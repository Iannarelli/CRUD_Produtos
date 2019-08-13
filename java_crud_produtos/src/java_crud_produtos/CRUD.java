package java_crud_produtos;

import org.json.JSONArray;
import org.json.JSONObject;
import org.simpleframework.http.Query;

public class CRUD {

	private static ConexaoAPI conexao = new ConexaoAPI();
	
	public boolean adicionarProduto (Query query) throws Exception {
		String json_string = "{\"token\":\""+conexao.getToken()+"\",\"codigo\":\""+query.get("codigo")+"\",\"nome\":\""
				+query.get("nome")+"\",\"preco\":"+query.getFloat("preco")+",\"tipo\":\""+query.get("tipo")+"\",\"descricao\":\""
				+query.get("descricao")+"\"}";
		String[] retorno = conexao.conectarAPI(conexao.getUrl()+"produto/add", json_string, "POST");
//		System.out.println(retorno[0].toString() + " *** " + retorno[1].toString() + " *** " + retorno[2].toString());
		if (new JSONObject(retorno[2]).get("error").toString().equalsIgnoreCase("false")) {
//			System.out.println(new JSONObject(retorno[2]).get("error").toString());
			return false;
		}
		else {
//			System.out.println("Produto já cadastrado!");
			return true;
		}
	}
	
	public JSONArray listarProdutos() throws Exception {
		String[] retorno = conexao.conectarAPI(conexao.getUrl()+"produtos/"+conexao.getToken(), "", "GET");
		System.out.println("retorno 2: " + retorno[2]);
		JSONObject teste = new JSONObject(retorno[2]);
		System.out.println("teste: " + teste.toString());
		System.out.println("teste produtos: " + teste.get("produtos"));
		JSONArray produtos = new JSONArray(new JSONObject(retorno[2]).get("produtos").toString());
		System.out.println("primeiro json: " + produtos.toString());
		System.out.println("primeiro item: " + produtos.get(0).toString());
//		JSONObject produtos = new JSONObject(itens.get("produtos"));
//		System.out.println("segundo json: " + produtos.get.toString());
		return produtos;
	}
	
//	public boolean produtoExistente(Long codigo) throws Exception {
//		conexao.conectarAPI("http://briansilva1.zeedhi.com/workfolder/processoseletivo/sistemaprodutos/produtos/CQG6BJPWT9GR4P", "", "GET");
//		System.out.println(conexao.getConteudo()[2]);
//		return false;
//	}
}
