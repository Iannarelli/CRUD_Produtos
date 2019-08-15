package java_crud_produtos;

import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.util.Scanner;

import org.json.JSONArray;
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
					String resposta = crud.adicionarProduto(request.getQuery());
					if(resposta.equalsIgnoreCase("true"))
						enviaResposta(Status.NOT_IMPLEMENTED, response, "");
					else if(resposta.equalsIgnoreCase("false"))
						enviaResposta(Status.CREATED, response, "");
					else
						enviaResposta(Status.BAD_REQUEST, response, resposta);
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		if (path.equalsIgnoreCase("/produtos/listar") && method.equalsIgnoreCase("get")) {
			try {
				JSONArray json = crud.listarProdutos();
				enviaResposta(Status.OK, response, json.toString());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (path.equalsIgnoreCase("/produto/delete") && method.equalsIgnoreCase("post")) {
				try {
					crud.deletarProduto(request.getContent());
					enviaResposta(Status.OK, response, "");
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
		if (path.equalsIgnoreCase("/produto/update") && method.equalsIgnoreCase("post")) {
			try {
				String resposta = crud.alterarProduto(request.getQuery());
				if (resposta.equalsIgnoreCase("true"))
					enviaResposta(Status.NOT_FOUND, response, "");
				else if (resposta.equalsIgnoreCase("false"))
					enviaResposta(Status.OK, response, "");
				else
					enviaResposta(Status.BAD_REQUEST, response, resposta);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void enviaResposta(Status status, Response response, String json) throws Exception {
		PrintStream body = response.getPrintStream();

		response.setValue("Access-Control-Allow-Origin", "*");
		response.setValue("Content-Type", "application/x-www-form-urlencoded");

		response.setStatus(status);
		if (json.equalsIgnoreCase(""))
			body.println();
		else
			body.println(json);
		body.close();
	}

	public static void main(String[] args) throws Exception {

		int porta = 880;

		Container container = new Aplicacao();
		ContainerSocketProcessor servidor = new ContainerSocketProcessor(container);
		Connection conexao = new SocketConnection(servidor);
		SocketAddress endereco = new InetSocketAddress(porta);
		conexao.connect(endereco);

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
