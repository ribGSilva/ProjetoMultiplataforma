package com.ribeiro.gabriel.compras.domain.factories

import com.ribeiro.gabriel.compras.domain.entities.Product
import com.ribeiro.gabriel.compras.exceptions.InvalidDescriptionException
import com.ribeiro.gabriel.compras.exceptions.InvalidNameException
import com.ribeiro.gabriel.compras.exceptions.InvalidPriceException

object ProductFactory {

    private const val MINIMUM_VALUE_POSSIBLE = 0.0.toFloat()

    @Throws(InvalidNameException::class, InvalidPriceException::class, InvalidDescriptionException::class)
    fun createNewProduct(name: String, price: Float, description: String): Product {
        if (name.isBlank())
            throw InvalidNameException("Invalid name in parameter")

        if (price < MINIMUM_VALUE_POSSIBLE)
            throw InvalidPriceException("Invalid price in parameter")

        if (description.isBlank())
            throw InvalidDescriptionException("Invalid description in parameter");

        return Product(name, price, description)
    }
}