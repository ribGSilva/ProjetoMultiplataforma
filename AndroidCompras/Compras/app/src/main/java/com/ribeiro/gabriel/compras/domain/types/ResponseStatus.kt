package com.ribeiro.gabriel.compras.domain.types

import com.ribeiro.gabriel.compras.exceptions.UnknownStatusResponseException

enum class ResponseStatus (val value: Int){
    SUCCESS(200),
    JSON_CONVERSION_FAIL(201),
    UNKNOWN_ERROR(202);

    companion object {
        @Throws(UnknownStatusResponseException::class)
        fun getResponseStatusByValue(valueToSearch: Int) : ResponseStatus  {
            ResponseStatus.values().forEach{ if (it.value == valueToSearch) return it }
            throw UnknownStatusResponseException("Unknown status response received")
        }
    }
}