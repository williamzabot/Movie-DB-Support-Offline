package com.williamzabot.fullmoviedb.data.remote.retrofit.service

import com.williamzabot.fullmoviedb.data.remote.model.MovieResults
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface MovieService {

    @GET("movie/popular/")
    suspend fun getPopularMovies(
        @Header("Authorization") token: String,
        @Query("page") page : Int
    ): Response<MovieResults>
}