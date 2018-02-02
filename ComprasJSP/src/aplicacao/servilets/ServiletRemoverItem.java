package aplicacao.servilets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ServiletRemoverItem
 */
@WebServlet("/ServiletRemoverItem")
public class ServiletRemoverItem extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String URL_REMOVER_PRODUTO;
	
       
    @Override
    public void init(ServletConfig config) throws ServletException {
    	super.init(config);
    	
    	URL_REMOVER_PRODUTO = "http://localhost:8080/ServidorDeProdutos/RestDeProdutos/removerProdutoNaPosicao/";
    }

	@Override
	protected void service(HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		super.service(request, response);
		
		
	}
    
    

}
