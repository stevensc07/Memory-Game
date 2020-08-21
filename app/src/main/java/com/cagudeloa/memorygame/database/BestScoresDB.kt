package com.cagudeloa.memorygame.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [BestScores::class], version = 1)
abstract class BestScoresDB: RoomDatabase() {

    abstract fun getBestScoresDao(): BestScoresDao

    companion object {

        @Volatile private var instance: BestScoresDB? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also {
                instance = it
            }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            BestScoresDB::class.java,
            "bestScoresDB"
        ).build()
    }
}