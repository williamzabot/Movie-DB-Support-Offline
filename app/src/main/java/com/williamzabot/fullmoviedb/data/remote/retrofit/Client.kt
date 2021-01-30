package com.williamzabot.fullmoviedb.data.remote.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Client {
    fun retrofitInstance(): Retrofit {
        return Retrofit.Builder().baseUrl("https://api.themoviedb.org/3/")
            .client(client())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun client() = OkHttpClient.Builder().apply {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BASIC
        addInterceptor(logging)
    }.build()
}