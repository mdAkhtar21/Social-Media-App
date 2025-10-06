package com.example.socialmedialapp.common.data.remote
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.utils.EmptyContent.headers
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.http.path
import io.ktor.http.takeFrom
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal abstract class KtorApi {
    val BASE_URL = "http://192.168.31.194:8000/"
    val client= HttpClient {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                useAlternativeNames = false
            })
        }
    }
    fun HttpRequestBuilder.endPoint(path: String){
        url{
            takeFrom(BASE_URL)
            path(path)
            contentType(ContentType.Application.Json)
        }
    }

    fun HttpRequestBuilder.setToken(token:String){
        headers{
            append(name = "Authorization", value = "Bearer ${token}")
        }
    }
}