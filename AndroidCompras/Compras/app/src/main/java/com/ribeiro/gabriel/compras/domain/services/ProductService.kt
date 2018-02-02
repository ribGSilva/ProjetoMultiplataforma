package com.ribeiro.gabriel.compras.domain.services

import android.content.ContentValues.TAG
import android.util.Log
import com.google.gson.Gson
import com.ribeiro.gabriel.compras.domain.entities.Product
import com.ribeiro.gabriel.compras.domain.factories.HttpUrlFactory
import com.ribeiro.gabriel.compras.domain.factories.RestRequestResultFactory
import com.ribeiro.gabriel.compras.domain.transportObject.RestRequestResult
import com.ribeiro.gabriel.compras.domain.types.*
import org.apache.hc.client5.http.entity.EntityBuilder
import org.apache.hc.client5.http.classic.methods.HttpPost
import org.apache.hc.client5.http.impl.classic.HttpClients
import org.apache.hc.core5.http.ContentType
import java.io.InputStream

object ProductService {

    fun registerNewProduct(newProduct: Product): RestRequestResult {
        val httpUrlPost = HttpUrlFactory.createUrl(
                HttpProtocol.HTTP,
                HostAddress.REST_PRODUCTS,
                ServerName.PRODUCT_SERVER,
                RestName.PRODUCT_REST,
                RestMethod.ADD_PRODUCT,
                null
        )

        val httpClient = HttpClients.createDefault()
        val httpPost = HttpPost(httpUrlPost.httpUrl)

        val entityBuilder = EntityBuilder.create()
        entityBuilder.contentType = ContentType.APPLICATION_JSON
        entityBuilder.text = Gson().toJson(newProduct)
        httpPost.entity = entityBuilder.build()


        val httpResponse = httpClient.execute(httpPost)

        val entityResponse = httpResponse.entity
        val statusResponse = httpResponse.code

        val responseStatus = ResponseStatus.getResponseStatusByValue(statusResponse)
        val responseMessage = entityResponse?.content.toString()

        return RestRequestResultFactory.createRestRequestResult(responseStatus, responseMessage)
    }

    fun removeProductAtPosition(position: Int): RestRequestResult {
        val httpUrlGet = HttpUrlFactory.createUrl(
                HttpProtocol.HTTP,
                HostAddress.REST_PRODUCTS,
                ServerName.PRODUCT_SERVER,
                RestName.PRODUCT_REST,
                RestMethod.REMOVE_PRODUCT,
                Array(1) { position }
        )

        val httpClient = HttpClients.createDefault()
        val httpGet = HttpPost(httpUrlGet.httpUrl)

        val httpResponse = httpClient.execute(httpGet)

        val entityResponse = httpResponse.entity
        val statusResponse = httpResponse.code

        val responseStatus = ResponseStatus.getResponseStatusByValue(statusResponse)
        val responseMessage = entityResponse?.content.toString()

        return RestRequestResultFactory.createRestRequestResult(responseStatus, responseMessage)
    }

    fun getProductList(): RestRequestResult {
        val httpUrlGet = HttpUrlFactory.createUrl(
                HttpProtocol.HTTP,
                HostAddress.REST_PRODUCTS,
                ServerName.PRODUCT_SERVER,
                RestName.PRODUCT_REST,
                RestMethod.GET_PRODUCTS,
                null
        )

        val httpClient = HttpClients.createDefault()
        val httpGet = HttpPost(httpUrlGet.httpUrl)

        val httpResponse = httpClient.execute(httpGet)

        val entityResponse = httpResponse.entity
        val statusResponse = httpResponse.code

        val responseStatus = ResponseStatus.getResponseStatusByValue(statusResponse)
        val inputStreamOfResponse = entityResponse?.content
        val responseMessage = inputStreamToString(inputStreamOfResponse)

        var productList: Array<Product>? = null

        try {
            productList = Gson().fromJson(responseMessage, Array<Product>::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.w(TAG, "Fail to parse json to Array<Product>")
        }
        return if (productList != null) {
            RestRequestResultFactory.createRestRequestResult(responseStatus, productList)
        } else {
            RestRequestResultFactory.createRestRequestResult(responseStatus, responseMessage)
        }
    }

    private fun inputStreamToString (inputStream: InputStream?) : String? {
        if (inputStream == null) return null

        val sizeOfByteArray = inputStream.available()
        var stringBytes = ByteArray(sizeOfByteArray)
        val sizeOfString = inputStream.read(stringBytes, 0, sizeOfByteArray)
        return String(stringBytes, 0, sizeOfString)
    }
}