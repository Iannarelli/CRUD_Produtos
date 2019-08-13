package java_crud_produtos;

import java.awt.Desktop;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.URI;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONObject;
import org.simpleframework.http.Request;
import org.simpleframework.http.Response;
import org.simpleframework.http.Status;
import org.simpleframework.http.core.Container;
import org.simpleframework.http.core.ContainerSocketProcessor;
import org.simpleframework.transport.connect.Connection;
import org.simpleframework.transport.connect.SocketConnection;

public class Aplicacao implements Container {
	
	public static CRUD crud = new CRUD();

	public void handle(Request request, Response response) {
		String path = request.getPath().toString();
		String method = request.getMethod();
		if (path.equalsIgnoreCase("/produto/add") && method.equalsIgnoreCase("post")) {
				try {
					if(crud.adicionarProduto(request.getQuery()))
						enviaResposta(Status.NOT_IMPLEMENTED, response, "");
					else
						enviaResposta(Status.CREATED, response, "");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			System.out.println(Long.parseLong(request.getQuery().get("codigo")));
			System.out.println("vou fazer isso");
		}
		if (path.equalsIgnoreCase("/produtos/listar") && method.equalsIgnoreCase("get")) {
			try {
				JSONArray json = crud.listarProdutos();
				enviaResposta(Status.OK, response, json.toString());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		System.out.println(Long.parseLong(request.getQuery().get("codigo")));
		System.out.println("vou fazer isso");
	}
		System.out.println("Request: " + request.getQuery().toString());
		System.out.println(path + " / " + method);
	}

	private void enviaResposta(Status status, Response response, String json/*, JSONObject JSON*/) throws Exception {
		PrintStream body = response.getPrintStream();
//		long time = System.currentTimeMillis();
		response.setValue("Access-Control-Allow-Origin", "*");
		response.setValue("Content-Type", "application/x-www-form-urlencoded");
//		response.setValue("Server", "");
//		response.setDate("Date", time);
//		response.setDate("Last-Modified", time);
		response.setStatus(status);
		if (json.equalsIgnoreCase(""))
			body.println();
		else
			body.println(json);
		body.close();
	}

	public static void main(String[] args) throws Exception {

//		String json_string = "{\"message\":\"Produtos recuperados com sucesso.\",\"error\":false,\"produtos\":[]}";
//		System.out.println("A string ficou: " + json_string);
//		JSONObject json = new JSONObject(json_string);
//		System.out.println(json.get("message"));
		int porta = 880;

		Container container = new Aplicacao();
		ContainerSocketProcessor servidor = new ContainerSocketProcessor(container);
		Connection conexao = new SocketConnection(servidor);
		SocketAddress endereco = new InetSocketAddress(porta);
		conexao.connect(endereco);

		//Desktop.getDesktop().browse(new URI("https://pucweb-wesley-mouraria.azurewebsites.net/"));

		Scanner entrada = new Scanner(System.in);
		String a = "";
		while (!a.equalsIgnoreCase("y")) {
			System.out.println("Interromper o servidor? (y/n)");
			a = entrada.next();
		}
		entrada.close();
		conexao.close();
		servidor.stop();
		System.out.println("Servidor terminado.");
	}
}
