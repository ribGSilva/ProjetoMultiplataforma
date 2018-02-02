<%@ include file="/includes/Cabecalho.jsp"%>
<%@ include file="/includes/Menu.jsp"%>
<h1>Adicionar item</h1>
<hr />
<form action="adicionaItem">
	<fieldset class="cadastro">
		<legend class="cadastro">Cadastro De Produto</legend>
		<table class="cadastro">
			<tr class="cadastro">
				<th class="cadastro">Nome do produto:</th>
				<td class="cadastro"><input class="cadastro" type="text"
					name="nome" placeholder="Nome" required /></td>
			</tr>
			<tr class="cadastro">
				<th class="cadastro">Preco do produto:</th>
				<td class="cadastro"><input type="number" name="preco" min="0"
					value="0" step="0.01" placeholder="0.00" required /></td>
			</tr>
			<tr class="cadastro">
				<th class="cadastro">Descricao do produto:</th>
				<td class="cadastro"><input class="cadastro" type="text"
					name="descricao" placeholder="Descrição" required></td>
			</tr>
			<tr class="cadastro">
				<td><%=request.getAttribute("mensagem") == null ? "" : request.getAttribute("mensagem")%></td>
				<td class="cadastro"><input type="submit" value="Adicionar" />
				</td>
			</tr>
		</table>
	</fieldset>
</form>
<%@ include file="/includes/Rodape.jsp"%>