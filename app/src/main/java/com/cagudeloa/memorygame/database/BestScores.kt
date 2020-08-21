package com.cagudeloa.memorygame.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BestScores (
    @PrimaryKey
    val id: Int,
    val memoryScore: String,
    val sequenceScore: String
)
