package com.ribeiro.gabriel.compras.domain.factories

import com.ribeiro.gabriel.compras.domain.types.*
import com.ribeiro.gabriel.compras.domain.valueObject.HttpUrl
import java.lang.reflect.Method

object HttpUrlFactory {
    fun createUrl(protocol: HttpProtocol,
                  hostAddress: HostAddress,
                  serverName: ServerName,
                  restName: RestName,
                  restMethod: RestMethod,
                  parameters: Array<Any>?) : HttpUrl{

        return if (restMethod.requestMethod == RequestMethod.POST)
            createAPostMethod(protocol, hostAddress, serverName, restName, restMethod)
        else
            createAGetMethod(protocol, hostAddress, serverName, restName, restMethod, parameters)
    }

    private fun createAPostMethod(protocol: HttpProtocol,
                                  hostAddress: HostAddress,
                                  serverName: ServerName,
                                  restName: RestName,
                                  restMethod: RestMethod): HttpUrl {

        val url = "${protocol.httpProtocol}://${hostAddress.hostAddress}:${hostAddress.port}/${serverName.serverName}/" +
                "${restName.restName}/${restMethod.methodName}"

        return HttpUrl(url)
    }


    private fun createAGetMethod(protocol: HttpProtocol,
                                 hostAddress: HostAddress,
                                 serverName: ServerName,
                                 restName: RestName,
                                 restMethod: RestMethod,
                                 parameters: Array<Any>?): HttpUrl {

        val url = "${protocol.httpProtocol}://${hostAddress.hostAddress}:${hostAddress.port}/${serverName.serverName}/" +
                "${restName.restName}/${restMethod.methodName}"

        if (parameters != null)
            url.plus(transformParametersToUrl(parameters))

        return HttpUrl(url)
    }

    private fun transformParametersToUrl(parameters: Array<Any>): String{
        var classFromObject : Class<Any>
        var toStringMethod : Method
        var stringToSum : String
        val urlParams = String()

        parameters.forEach {
            classFromObject = it.javaClass
            toStringMethod = classFromObject.getMethod("toString")
            stringToSum = toStringMethod.invoke(it) as String
            urlParams.plus("/$stringToSum")
            }

        return urlParams
    }
}