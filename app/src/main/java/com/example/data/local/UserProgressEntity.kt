package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_progress")
data class UserProgressEntity(
    @PrimaryKey
    val userId: String = "local_learner",
    val userName: String = "Learner",
    val currentLevel: Int = 0,
    val unlockedLevel: Int = 12,
    val totalXp: Int = 680,
    val streakDays: Int = 4,
    val totalSpeakingMinutes: Int = 42,
    val totalQuestionsAnswered: Int = 145,
    val speakingScore: Int = 74,
    val grammarScore: Int = 82,
    val vocabularyScore: Int = 78,
    val listeningScore: Int = 85,
    val readingScore: Int = 80,
    val writingScore: Int = 70,
    val fluencyScore: Int = 68,
    val learningGoal: String = "Daily Conversation", // Daily Conversation, Job Interview, College, Travel, Business, General Fluency
    val dailyTargetMinutes: Int = 15,
    val isPremium: Boolean = false
)
