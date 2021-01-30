package com.williamzabot.fullmoviedb.data.db.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
class PopularMovie(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val data: String,
    val url : String
)