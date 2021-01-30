package com.williamzabot.fullmoviedb.data.db.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.williamzabot.fullmoviedb.data.db.local.dao.PopularDAO
import com.williamzabot.fullmoviedb.data.db.local.entity.PopularMovie

@Database(entities = [PopularMovie::class], version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract val popularDAO: PopularDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            synchronized(this) {
                var instance: AppDatabase? = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "app_database"
                    ).fallbackToDestructiveMigration()
                        .build()
                }

                return instance
            }
        }
    }
}