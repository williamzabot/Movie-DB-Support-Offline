package com.williamzabot.fullmoviedb.data.remote.model

import com.google.gson.annotations.SerializedName

data class MovieResults(
    val page: Int,
    @SerializedName("results")
    val movies: List<Movie>,
    val total_pages: Int,
    val total_results: Int
)