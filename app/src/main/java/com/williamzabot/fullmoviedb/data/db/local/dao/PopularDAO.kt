package com.williamzabot.fullmoviedb.data.db.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.williamzabot.fullmoviedb.data.db.local.entity.PopularMovie

@Dao
interface PopularDAO {

    @Insert
    suspend fun insert(movie: PopularMovie)

    @Query("DELETE FROM movies")
    suspend fun deleteAll()

    @Query("SELECT * FROM movies")
    suspend fun getMovies(): List<PopularMovie>
}