package aplicacao.rests;

import java.util.ArrayList;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import aplicacao.entidades.Produto;

@Path("/RestDeProdutos")
public class RestDeProdutos {

	private Gson gson;
	private ArrayList<Produto> listaDeProdutos;
	private static RestDeProdutos instancia;

	private RestDeProdutos() {
		listaDeProdutos = new ArrayList<>();
		Produto p = new Produto();
		p.nome = "Arroz";
		p.preco = 5.99;
		p.descricao = "Asiatico";
		listaDeProdutos.add(p);

		p = new Produto();
		p.nome = "Tomate";
		p.preco = 35.99;
		p.descricao = "De ouro";
		listaDeProdutos.add(p);

		gson = new Gson();
	}

	public static synchronized RestDeProdutos pegarInstancia() {
		if (instancia == null)
			instancia = new RestDeProdutos();
		return instancia;
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/adicionarProduto")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response adicionarProduto(String produtoEmJson) {
		System.out.println("metodo: adicionarProduto -- com parametro : " + produtoEmJson);
		String corpoDaResposta;
		Response resposta;

		try {
			Produto produto = gson.fromJson(produtoEmJson, Produto.class);

			if (produto != null) {
				listaDeProdutos.add(produto);

				corpoDaResposta = "ok";
				resposta = Response.status(200).entity(corpoDaResposta).header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
			} else {
				corpoDaResposta = "Falha ao converter";
				resposta = Response.status(201).entity(corpoDaResposta).header("Access-Control-Allow-Origin", "*")
						.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
			}
		} catch (Exception e) {
			e.printStackTrace();
			corpoDaResposta = "Falha em algo";
			resposta = Response.status(202).entity(corpoDaResposta).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
		}

		System.out.println("Resposta: " + resposta.getEntity());

		return resposta;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@Path("/adicionarProduto/{nome}/{preco}/{descricao}")
	public Response adicionarProduto(@PathParam("nome") String nome, @PathParam("preco") double preco,
			@PathParam("descricao") String descricao) {
		System.out.println("metodo: adicionarProduto -- com parametros : nome - " + nome + ";  preco - " + preco
				+ ";  descricao - " + descricao);
		String corpoDaResposta;
		Response resposta;

		try {
			Produto produto = new Produto();
			produto.nome = nome;
			produto.preco = preco;
			produto.descricao = descricao;

			listaDeProdutos.add(produto);

			corpoDaResposta = "ok";
			resposta = Response.status(200).entity(corpoDaResposta).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();

		} catch (Exception e) {
			e.printStackTrace();
			corpoDaResposta = "Falha em algo";
			resposta = Response.status(202).entity(corpoDaResposta).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
		}

		System.out.println("Resposta: " + resposta.getEntity());

		return resposta;
	}

	@GET
	@Path("/pegarProdutos")
	@Produces(MediaType.APPLICATION_JSON)
	public Response pegarProdutos() {
		System.out.println("metodo: pegarProdutos ");
		String corpoDaResposta;
		Response resposta;

		try {
			String produtosEmJson = gson.toJson(listaDeProdutos);

			corpoDaResposta = produtosEmJson;
			resposta = Response.status(200).entity(corpoDaResposta).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
		} catch (Exception e) {
			e.printStackTrace();
			corpoDaResposta = "Falha em algo";
			resposta = Response.status(202).entity(corpoDaResposta).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
		}

		System.out.println("Resposta: " + resposta.getEntity());

		return resposta;
	}

	@GET
	@Path("/removerProdutoNaPosicao/{posicao}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response removerProdutoNaPosicao(@PathParam("posicao") int posicao) {
		System.out.println("metodo: removerProdutoNaPosicao -- posicao: " + posicao);
		String corpoDaResposta;
		Response resposta;

		try {
			listaDeProdutos.remove(posicao);
			corpoDaResposta = "";
			resposta = Response.status(200).entity(corpoDaResposta).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();

		} catch (Exception e) {
			e.printStackTrace();
			corpoDaResposta = "Falha em algo";
			resposta = Response.status(202).entity(corpoDaResposta).header("Access-Control-Allow-Origin", "*")
					.header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS, HEAD").build();
		}

		System.out.println("Resposta: " + resposta.getEntity());

		return resposta;
	}

}
