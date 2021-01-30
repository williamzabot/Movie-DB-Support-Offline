package com.williamzabot.fullmoviedb.data.remote.repository

import com.williamzabot.fullmoviedb.data.remote.exception.ErrorToken
import com.williamzabot.fullmoviedb.data.remote.model.MovieResults
import com.williamzabot.fullmoviedb.data.remote.retrofit.Client
import com.williamzabot.fullmoviedb.data.remote.retrofit.service.MovieService
import java.lang.Exception
import com.williamzabot.fullmoviedb.utils.Result

const val TOKEN = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwZWYxNWJhYjY5YmNkNzQyNDMxM2ZhYWNkMmI4NDgwZSIsInN1YiI6IjVmZDNmMDEzYTBiNmI1MDAzZWZhZjQ4MSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.Tpuf2lzDd_z6PiM_iLafE1eWvz71lYWYb0W2jmVZALU"

class MovieRepository {

    private val client = Client.retrofitInstance().create(MovieService::class.java)

    suspend fun getMovie(page : Int): Result<MovieResults> {
        val response = client.getPopularMovies(TOKEN, page)
        return when (response.code()) {
            200 -> Result.Success(response.body()!!)
            401 -> Result.Failure(ErrorToken)
            else -> Result.Failure(Exception())
        }
    }
}