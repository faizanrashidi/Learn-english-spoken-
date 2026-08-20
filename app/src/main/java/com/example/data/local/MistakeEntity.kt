package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "mistakes")
data class MistakeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val learnerSaid: String,
    val correctSentence: String,
    val hindiExplanation: String,
    val grammarTopic: String,
    val mistakeCount: Int = 1,
    val masteryScore: Int = 0, // 0 to 100%
    val lastPracticedTimestamp: Long = System.currentTimeMillis()
)
