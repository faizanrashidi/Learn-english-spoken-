package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.MistakeEntity
import com.example.data.local.UserProgressEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class EnglishLearningRepository(private val db: AppDatabase) {

    val userProgressFlow: Flow<UserProgressEntity> = db.userProgressDao().getUserProgressFlow().map {
        it ?: UserProgressEntity()
    }

    val allMistakesFlow: Flow<List<MistakeEntity>> = db.mistakeDao().getAllMistakesFlow()

    suspend fun recordMistake(
        learnerSaid: String,
        correctSentence: String,
        hindiExplanation: String,
        grammarTopic: String
    ) {
        val existing = db.mistakeDao().findByCorrectSentence(correctSentence)
        if (existing != null) {
            val updated = existing.copy(
                learnerSaid = learnerSaid,
                mistakeCount = existing.mistakeCount + 1,
                lastPracticedTimestamp = System.currentTimeMillis()
            )
            db.mistakeDao().updateMistake(updated)
        } else {
            db.mistakeDao().insertMistake(
                MistakeEntity(
                    learnerSaid = learnerSaid,
                    correctSentence = correctSentence,
                    hindiExplanation = hindiExplanation,
                    grammarTopic = grammarTopic,
                    mistakeCount = 1,
                    masteryScore = 20
                )
            )
        }
    }

    suspend fun markMistakePracticed(mistakeId: Long, success: Boolean) {
        // Boost mastery score
        // We can update the mistake in database
    }

    suspend fun addXpAndSpeakingTime(xpEarned: Int, speakingSeconds: Int) {
        val current = db.userProgressDao().getUserProgress() ?: UserProgressEntity()
        val minutesToAdd = maxOf(1, speakingSeconds / 60)
        val updated = current.copy(
            totalXp = current.totalXp + xpEarned,
            totalSpeakingMinutes = current.totalSpeakingMinutes + minutesToAdd,
            totalQuestionsAnswered = current.totalQuestionsAnswered + 1
        )
        db.userProgressDao().insertOrUpdate(updated)
    }

    suspend fun unlockNextLevel(completedLevel: Int) {
        val current = db.userProgressDao().getUserProgress() ?: UserProgressEntity()
        val newUnlocked = maxOf(current.unlockedLevel, completedLevel + 1)
        val updated = current.copy(
            currentLevel = completedLevel + 1,
            unlockedLevel = newUnlocked,
            totalXp = current.totalXp + 150
        )
        db.userProgressDao().insertOrUpdate(updated)
    }

    suspend fun updateGoal(newGoal: String) {
        val current = db.userProgressDao().getUserProgress() ?: UserProgressEntity()
        db.userProgressDao().insertOrUpdate(current.copy(learningGoal = newGoal))
    }

    suspend fun togglePremium(isPremium: Boolean) {
        val current = db.userProgressDao().getUserProgress() ?: UserProgressEntity()
        db.userProgressDao().insertOrUpdate(current.copy(isPremium = isPremium))
    }

    suspend fun deleteMistake(id: Long) {
        db.mistakeDao().deleteMistakeById(id)
    }
}
