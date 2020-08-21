package com.cagudeloa.memorygame.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BestScoresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addScores(bestScores : BestScores)

    @Query("SELECT * FROM  BestScores")
    suspend fun getBestScores() : BestScores
    
}