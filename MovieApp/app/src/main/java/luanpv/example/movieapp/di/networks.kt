package com.pgbank.personal.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pgbank.personal.constants.DatasourceProperties
import com.pgbank.personal.data.Service
import com.pgbank.personal.data.remote.MainRemote
import luanpv.example.movieapp.BuildConfig
import okhttp3.Cache
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier

val networks = module {
    single { createOkHttpCache(get()) }
    single { createOkHttpClient(get()) }
    factory<OkHttpClient>(named("newOkHttp"))
    {
        newBuildOkHttp(
            get(),
            get()
        )
    }
    single<Service> { createService(get(named("newOkHttp"))) }
    single { createLogging() }
}

fun createOkHttpClient(
    cache: Cache
): OkHttpClient {

    return OkHttpClient.Builder()
        .cache(cache)
        .build()
}

fun createOkHttpCache(context: Context): Cache {
    return Cache(context.cacheDir, Long.MAX_VALUE)
}

fun newBuildOkHttp(
    client: OkHttpClient,
    logging: HttpLoggingInterceptor
): OkHttpClient {

    if (BuildConfig.DEBUG) {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
    } else
        logging.setLevel(HttpLoggingInterceptor.Level.NONE)
    val builder = client.newBuilder()
        .addInterceptor(logging)
        .connectTimeout(DatasourceProperties.TIMEOUT_CONNECT, TimeUnit.SECONDS)
        .readTimeout(DatasourceProperties.TIMEOUT_READ, TimeUnit.SECONDS)
        .writeTimeout(DatasourceProperties.TIMEOUT_READ, TimeUnit.SECONDS)
        .followRedirects(false)
        .followSslRedirects(false)
        .retryOnConnectionFailure(false)
    return builder.build()
}

fun createLogging(): HttpLoggingInterceptor {
    return HttpLoggingInterceptor();
}

fun createService(client: OkHttpClient): Service {
    return MainRemote(client).getService()
}



