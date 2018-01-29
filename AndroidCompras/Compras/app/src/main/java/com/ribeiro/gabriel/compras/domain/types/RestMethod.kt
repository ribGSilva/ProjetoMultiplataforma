package com.ribeiro.gabriel.compras.domain.types

enum class RestMethod(val methodName: String, val requestMethod: RequestMethod) {
    ADD_PRODUCT("adicionarProduto", RequestMethod.POST),
    GET_PRODUCTS("pegarProdutos", RequestMethod.GET),
    REMOVE_PRODUCT("removerProdutoNaPosicao", RequestMethod.GET)
}