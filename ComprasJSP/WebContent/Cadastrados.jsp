<%@ include file="/includes/Cabecalho.jsp"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/includes/Menu.jsp"%>
	<h1>Ver itens cadastrados</h1>
	<hr />
	<table class="view" id="exportthis">
		<tr class="view">
			<th class="view">Nome</th>
			<th class="view">Descrição</th>
			<th class="view">Preço</th>
			<th class="view">Remover</th>
		</tr>
		<c:forEach var="item" items="${itens}" varStatus="id">
			<tr>
				<td class="view" style="background-color:#${ id.count % 2 == 0 ? 'f1f1f1' : 'ffffff' }">${item.nome}</td>
				<td class="view" style="background-color:#${ id.count % 2 == 0 ? 'f1f1f1' : 'ffffff' }">${item.descricao}</td>
				<td class="view" style="background-color:#${ id.count % 2 == 0 ? 'f1f1f1' : 'ffffff' }">R$${item.preco}</td>
				<td class="view" style="background-color:#${ id.count % 2 == 0 ? 'f1f1f1' : 'ffffff' }">${item.nome}</td>
			</tr>
		</c:forEach>
		<tr>
	    	<th class="view" colspan="2">Total</th>
	    	<th class="view">R$${ total }</th>
	    	<th class="view">
	    		<form action="listarItens">
	    			<input class="view" type="submit" value="Atualuzar">
	    		</form>
	    	</th>
	  	</tr>
	</table>
<%@ include file="/includes/Rodape.jsp"%>