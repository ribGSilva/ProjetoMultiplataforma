package com.ribeiro.gabriel.compras.domain.entities

import com.google.gson.annotations.SerializedName


data class Product(@SerializedName("name") var name: String,
                   @SerializedName("price")var price: Float,
                   @SerializedName("description") var description: String)