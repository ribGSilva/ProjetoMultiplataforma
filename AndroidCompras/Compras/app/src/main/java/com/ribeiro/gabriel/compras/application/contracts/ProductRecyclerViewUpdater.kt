package com.ribeiro.gabriel.compras.application.contracts

import com.ribeiro.gabriel.compras.domain.entities.Product

interface ProductRecyclerViewUpdater {
    fun updateProductRecyclerView(newList: ArrayList<Product>)
}