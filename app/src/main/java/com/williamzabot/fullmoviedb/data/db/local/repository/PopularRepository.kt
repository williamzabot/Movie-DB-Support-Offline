package com.williamzabot.fullmoviedb.data.db.local.repository

import com.williamzabot.fullmoviedb.data.db.local.dao.PopularDAO
import com.williamzabot.fullmoviedb.data.db.local.entity.PopularMovie

class PopularRepository(private val popularDAO: PopularDAO) {

    suspend fun insertMovie(movie: PopularMovie) {
        popularDAO.insert(movie)
    }

    suspend fun deleteAll() {
        popularDAO.deleteAll()
    }

    suspend fun getPopularMovies(): List<PopularMovie> {
        return popularDAO.getMovies()
    }

}