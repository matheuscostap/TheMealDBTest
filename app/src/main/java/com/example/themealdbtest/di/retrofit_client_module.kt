package com.example.themealdbtest.di

import com.example.themealdbtest.repository.MealDBDataSource
import okhttp3.OkHttpClient
import org.koin.dsl.module.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val retrofit_client_module = module {
    single { createOkHttpCLient() }
    single { createWebService<MealDBDataSource>(get(),getProperty(Properties.BASE_URL))}
}

object Properties{
    const val BASE_URL = "BASE_URL"
}

fun createOkHttpCLient(): OkHttpClient {
    return OkHttpClient.Builder()
        .connectTimeout(60L, TimeUnit.SECONDS)
        .readTimeout(60L, TimeUnit.SECONDS)
        .build()
}

inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String): T {
    val retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    return retrofit.create(T::class.java)
}