package com.williamzabot.fullmoviedb.data.remote.model

import com.williamzabot.fullmoviedb.data.db.local.entity.PopularMovie

data class Movie(
    val adult: Boolean,
    val backdrop_path: String,
    val genre_ids: List<Int>,
    val id: Int,
    val original_language: String,
    val original_title: String,
    val overview: String,
    val popularity: Double,
    val poster_path: String,
    val release_date: String,
    val title: String,
    val video: Boolean,
    val vote_average: Double,
    val vote_count: Int
)

fun Movie.toPopularMovie(): PopularMovie {
    val url = "https://image.tmdb.org/t/p/w154${poster_path}"
    return PopularMovie(
         title = title,data = release_date, url = url
    )
}
