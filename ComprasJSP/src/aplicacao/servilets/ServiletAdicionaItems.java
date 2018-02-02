package aplicacao.servilets;

import java.io.IOException;

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
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.HttpClients;

import com.google.gson.Gson;

import aplicacao.entidades.Produto;
import aplicacao.objetosDeTransporte.RestResposta;

/**
 * Servlet implementation class ServiletItems
 */
@WebServlet(name = "adicionaItem")
public class ServiletAdicionaItems extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String URL_CADASTRO_PRODUTO;
	
	public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
        URL_CADASTRO_PRODUTO = "http://localhost:8080/ServidorDeProdutos/RestDeProdutos/adicionarProduto";
    }

       
	@Override
	protected void service(HttpServletRequest httpServletRequest, 
			HttpServletResponse httpServletResponse) throws ServletException, IOException {
		
		String nome = httpServletRequest.getParameter("nome");
        String preco = httpServletRequest.getParameter("preco");
        String descricao = httpServletRequest.getParameter("descricao");
		
        try {
	        Produto produtoNovo = new Produto();
	        produtoNovo.nome = nome;
	        produtoNovo.descricao = descricao;
	        produtoNovo.preco = Double.parseDouble(preco);
	        
	        System.out.println(nome + "  -  " +  descricao + "  -  " + preco);
	        
	        RestResposta resposta = tentaCadastro(produtoNovo);
	        
	        if (resposta.status != 200)
	        	httpServletRequest.setAttribute("mensagem", resposta.mensagem);
	        else {
	        	httpServletRequest.setAttribute("mensagem", nome + " cadastrado com sucesso");
	        }
	        
	        ServletContext sc = this.getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/AdicionarItem.jsp");
	        rd.forward(httpServletRequest, httpServletResponse);
	        
        } catch(IOException e) {
        	httpServletRequest.setAttribute("mensagem", "Falha ao conectar com servidor");
        	
        	ServletContext sc = this.getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/AdicionarItem.jsp");
	        rd.forward(httpServletRequest, httpServletResponse);
        }
        catch (NumberFormatException e) {
        	httpServletRequest.setAttribute("mensagem", "Valor inválido");
        	
        	ServletContext sc = this.getServletContext();
	        RequestDispatcher rd = sc.getRequestDispatcher("/AdicionarItem.jsp");
	        rd.forward(httpServletRequest, httpServletResponse);
        } catch (NullPointerException e) {
        	return;
        }
        catch (Exception e) {
        	e.printStackTrace();
        	return;
        }
	}
	
	private RestResposta tentaCadastro(Produto produtoNovo) throws ClientProtocolException, IOException {
		HttpClient client = HttpClients.createDefault();
		HttpPost post = new HttpPost(URL_CADASTRO_PRODUTO);
		
		EntityBuilder builder = EntityBuilder.create();
		builder.setContentType(ContentType.APPLICATION_JSON);
		builder.setText(new Gson().toJson(produtoNovo));
		
		post.setEntity(builder.build());
		
		HttpResponse response = client.execute(post);
		
		RestResposta resposta = new RestResposta();
		resposta.status = response.getStatusLine().getStatusCode();
		String mensagem = "";
		try {
			mensagem = post.getEntity().getContent().toString();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		resposta.mensagem = mensagem;
		return resposta; 
	}

}
