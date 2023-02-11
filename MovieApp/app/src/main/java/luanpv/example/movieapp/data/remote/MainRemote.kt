package com.pgbank.personal.data.remote

import com.google.gson.GsonBuilder
import com.pgbank.personal.data.Service
import com.pgbank.personal.constants.DatasourceProperties
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainRemote(client: OkHttpClient){
    private val service: Service

    init {
        val gson = GsonBuilder()
            .setLenient()
            .disableHtmlEscaping()
            .create()
        service = Retrofit.Builder()
            .baseUrl(DatasourceProperties.getUrl())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
                
            .build()
            .create(
                Service::class.java
            )
    }

    fun getService(): Service {
        return service
    }
}