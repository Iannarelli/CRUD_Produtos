package java_crud_produtos;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConexaoAPI {

	private static final String TOKEN = "CQG6BJPWT9GR4P";
	private static final String URL = "http://briansilva1.zeedhi.com/workfolder/processoseletivo/sistemaprodutos/";
	
//	public String[] getConteudo() {
//		return conteudo;
//	}

	public String getToken() {
		return TOKEN;
	}

	public String getUrl() {
		return URL;
	}

	public String[] conectarAPI(String url, String json, String metodo) throws Exception {
//		String url = "http://briansilva1.zeedhi.com/workfolder/processoseletivo/sistemaprodutos/produto/7/delete";
//		String json = "{\n\t\"token\":  \"CQG6BJPWT9GR4P\",\n\t\"codigo\": \"7\",\n\t\"nome\":   \"TóRTA DE BAUNILHA\",\n\t\"preco\":  7.5,\n\t\"tipo\": \"S\",\n\t\"descricao\": \"TORTA CASEIRA SABOR BAUNILHA\"\n}";
		URL destino = new URL(url);
		HttpURLConnection conexao = (HttpURLConnection) destino.openConnection();
		System.out.println("Endereço: " + url);
		System.out.println("JSON: " + json.toString());
		System.out.println("Método: " + metodo);
//		conexao.setConnectTimeout(30000);
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
		System.out.println("código da resposta: " + conteudo[0]);
		
		conteudo[1] = conexao.getResponseMessage();
		System.out.println("resposta: " + conteudo[1]);
		
		BufferedReader leitura = new BufferedReader(new InputStreamReader(conexao.getInputStream(), "UTF-8"));
		conteudo[2] = leitura.readLine().toString();
		System.out.println("testando: " + conteudo[2]);
		leitura.close();
		return conteudo;
	}

//	public static void conectarAPI2(String metodo) throws Exception {
//		String url = "http://briansilva1.zeedhi.com/workfolder/processoseletivo/sistemaprodutos/produtos/CQG6BJPWT9GR4P";
//		URL objeto = new URL(url);
//		HttpURLConnection conexao = (HttpURLConnection) objeto.openConnection();
////		conexao.setConnectTimeout(30000);
//		conexao.setRequestProperty("Content-Type", "false");
//		conexao.setRequestProperty("processData", "false");
//		conexao.setRequestProperty("mimeType", "multipart/form-data");
//		conexao.setReadTimeout(0);
//		conexao.setRequestMethod(metodo.toUpperCase());	
//
//		int codResposta = conexao.getResponseCode();
//		System.out.println("código da resposta: " + codResposta);
//		
//		String resposta = conexao.getResponseMessage();
//		System.out.println("resposta: " + resposta);
//		
//		BufferedReader leitura = new BufferedReader(new InputStreamReader(conexao.getInputStream(), "UTF-8"));
//		String inputLine;
//		StringBuffer response = new StringBuffer();
//
//		while ((inputLine = leitura.readLine()) != null) {
//			response.append(inputLine);
//		}
//		leitura.close(); 
//		System.out.println("testando: " + response.toString());
//	}

//	public static void main (String[] args) throws Exception {
//		conectarAPI("GET", "", "");
//		System.out.println("2ª parte");
//		conectarAPI("POST", "", "");
//		System.out.println("3ª parte");
//		conectarAPI("GET", "", "");
//	}
}
