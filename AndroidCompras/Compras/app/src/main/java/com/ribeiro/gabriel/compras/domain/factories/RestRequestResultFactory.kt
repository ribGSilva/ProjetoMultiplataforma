package com.ribeiro.gabriel.compras.domain.factories

import com.ribeiro.gabriel.compras.domain.transportObject.RestRequestResult
import com.ribeiro.gabriel.compras.domain.types.ResponseStatus

object RestRequestResultFactory {
    fun createRestRequestResult(status: ResponseStatus, message: Any?): RestRequestResult {
        return RestRequestResult(status, message)
    }
}