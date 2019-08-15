package java_crud_produtos;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class ConexaoAPI {

	private static final String TOKEN = "CQG6BJPWT9GR4P";
	private static final String URL = "http://briansilva1.zeedhi.com/workfolder/processoseletivo/sistemaprodutos/";

	public String getToken() {
		return TOKEN;
	}

	public String getUrl() {
		return URL;
	}

	public String[] conectarAPI(String url, String json, String metodo) throws Exception {
		URL destino = new URL(url);
		HttpURLConnection conexao = (HttpURLConnection) destino.openConnection();
		conexao.setReadTimeout(0);
		conexao.setRequestMethod(metodo.toUpperCase());
		if (metodo.equalsIgnoreCase("GET")) {
			conexao.setRequestProperty("Content-Type", "false");
			conexao.setRequestProperty("processData", "false");
			conexao.setRequestProperty("mimeType", "multipart/form-data");
		}
		if (metodo.equalsIgnoreCase("POST")) {
			conexao.setRequestProperty("Content-Type", "application/json");
			conexao.setDoOutput(true);
			OutputStream requisicao = conexao.getOutputStream();
			requisicao.write(json.getBytes("UTF-8"));
			requisicao.close();
		}

		String[] conteudo = new String[3];
		conteudo[0] = String.valueOf(conexao.getResponseCode());
		conteudo[1] = conexao.getResponseMessage();
		
		BufferedReader leitura = new BufferedReader(new InputStreamReader(conexao.getInputStream(), "UTF-8"));
		conteudo[2] = leitura.readLine().toString();

		leitura.close();
		return conteudo;
	}
}
