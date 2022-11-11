package com.juanmi.gamertool.core.network

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient(
    private val baseUrl: String = "https://api.igdb.com/v4/"
) {

    companion object {
        const val clientId = "8orzksqv1wskerp8nn2kvipkf9pvn2"
        const val token = "Bearer j0tv6obv0m4orspbil21dglkdms1t3"
    }

    private var gson: Gson? = null
    private var retrofit: Retrofit? = null

    init {
        this.gson = GsonBuilder()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            .create()
        this.configureServices()
    }

    private fun configureServices() {
        val builder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor()
        logging.level = (HttpLoggingInterceptor.Level.BODY)

        val headers = Interceptor { chain ->
            val request: Request = chain.request().newBuilder()
                .addHeader("Client-ID", clientId)
                .addHeader("Authorization", token)
                .build()
            chain.proceed(request)
        }

        val client = builder
            .readTimeout(50, TimeUnit.SECONDS)
            .connectTimeout(50, TimeUnit.SECONDS)
            .addInterceptor(logging)
            .addInterceptor(headers)
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(getBaseURL())
            .addConverterFactory(GsonConverterFactory.create(this.gson!!))
            .client(client)
            .build()
    }

    private fun getBaseURL() = baseUrl

    fun <S> createService(serviceClass: Class<S>): S {
        return retrofit!!.create(serviceClass)
    }
}