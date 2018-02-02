package aplicacao.servilets;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

import aplicacao.entidades.Produto;

/**
 * Servlet implementation class ServiletListarItens
 */
@WebServlet(name = "listarItens")
public class ServiletListarItens extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String URL_LISTA_PRODUTO;
	private static String URL_REMOVER_PRODUTO;

	public void init(ServletConfig config) throws ServletException {
        super.init(config);

        URL_LISTA_PRODUTO = "http://localhost:8080/ServidorDeProdutos/RestDeProdutos/pegarProdutos";
    }

	
	protected void service(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		String retorno;
		try {
			retorno =  pegarListaDeItens();
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		try {
			ArrayList<Produto> itens = new ArrayList<>();
			Produto[] array = new Gson().fromJson(retorno, Produto[].class);
			Collections.addAll(itens, array);

			request.setAttribute("itens", itens);
			
			double total = 0.0;
			for (Produto produto: itens) {
				total += produto.preco;
			}
			
			request.setAttribute("total", String.format("%.2f", total));
			
			ServletContext sc = this.getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/Cadastrados.jsp");
	        rd.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();

			request.setAttribute("itens", new ArrayList<Produto>());
			request.setAttribute("mensagem", retorno);
			
			ServletContext sc = this.getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/Cadastrados.jsp");
	        rd.forward(request, response);
		}
	}
	
	private String pegarListaDeItens() throws ClientProtocolException, IOException {
		HttpClient client = HttpClients.createDefault();
		HttpGet get = new HttpGet(URL_LISTA_PRODUTO);
		
		HttpResponse response = client.execute(get);
		String retorno;
		try {
			InputStream input = response.getEntity().getContent();
			byte[] bytes = new byte[9000];
			int quantidade = input.read(bytes);
			retorno = new String(bytes, 0, quantidade);
		} catch (NullPointerException e) {
			retorno = "[]";
		}

		return retorno;
	}

}
