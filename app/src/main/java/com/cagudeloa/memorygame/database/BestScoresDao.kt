package com.cagudeloa.memorygame.database

import androidx.room.*

@Dao
interface BestScoresDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addScores(bestScores : BestScores)

    @Query("SELECT * FROM  BestScores WHERE id=:id")
    suspend fun getBestScores(id: Int) : BestScores

}