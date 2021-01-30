package com.williamzabot.fullmoviedb.features.home.popular.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource
import com.williamzabot.fullmoviedb.data.db.local.repository.PopularRepository
import com.williamzabot.fullmoviedb.data.remote.model.Movie
import com.williamzabot.fullmoviedb.data.remote.model.toPopularMovie
import com.williamzabot.fullmoviedb.data.remote.repository.MovieRepository
import com.williamzabot.fullmoviedb.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import java.lang.Exception

class MovieDataSource(
    private val movieRepository: MovieRepository,
    private val popularRepository: PopularRepository,
    private val netIsOn: Boolean
) :
    PageKeyedDataSource<Int, Movie>() {
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Movie>
    ) {
        if (netIsOn) {
            try {
                CoroutineScope(Default).launch {
                    when (val result = movieRepository.getMovie(1)) {
                        is Result.Success -> {
                            val movies = result.data.movies
                            for (movie in movies) {
                                popularRepository.insertMovie(movie.toPopularMovie())
                            }

                            callback.onResult(movies, 0, 2)
                        }
                    }
                }
            } catch (ex: Exception) {
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        if (netIsOn) {
            CoroutineScope(Default).launch {
                popularRepository.deleteAll()
            }
        }

    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        if (netIsOn) {
            try {
                CoroutineScope(Default).launch {
                    when (val result = movieRepository.getMovie(params.key)) {
                        is Result.Success -> {
                            val movies = result.data.movies
                            if (params.key <= 2) {
                                for (movie in movies) {
                                    popularRepository.insertMovie(movie.toPopularMovie())
                                }
                            }
                            if (params.key < result.data.total_pages) {
                                callback.onResult(movies, params.key + 1)
                            }
                        }
                        is Result.Failure -> {
                            Log.i("PAGING", result.exception.toString())
                        }
                    }
                }
            } catch (ex: Exception) {
            }


        }
    }

    override fun invalidate() {
        super.invalidate()
        CoroutineScope(Default).cancel()
    }


}