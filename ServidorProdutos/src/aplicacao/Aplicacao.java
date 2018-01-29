package aplicacao;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

import aplicacao.rests.RestDeProdutos;

public class Aplicacao extends Application {
	private Set<Object> servicos = new HashSet<Object>();
	private Set<Class<?>> vazio = new HashSet<Class<?>>();
	
	public Aplicacao(){
		servicos.add(RestDeProdutos.pegarInstancia());
	}

	public Set<Object> getSingletons() {
		return servicos;
	}

	public Set<Class<?>> getEmpty() {
		return vazio;
	}
}
