package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [MistakeEntity::class, UserProgressEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mistakeDao(): MistakeDao
    abstract fun userProgressDao(): UserProgressDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "bol_english_ai.db"
                ).addCallback(object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        // Seed initial progress and common mistakes sample
                        CoroutineScope(Dispatchers.IO).launch {
                            val database = getInstance(context)
                            database.userProgressDao().insertOrUpdate(
                                UserProgressEntity(
                                    userId = "local_learner",
                                    userName = "Learner",
                                    currentLevel = 12,
                                    unlockedLevel = 13,
                                    totalXp = 680,
                                    streakDays = 4,
                                    totalSpeakingMinutes = 42,
                                    totalQuestionsAnswered = 145,
                                    speakingScore = 74,
                                    grammarScore = 82,
                                    vocabularyScore = 78,
                                    listeningScore = 85,
                                    readingScore = 80,
                                    writingScore = 70,
                                    fluencyScore = 68
                                )
                            )
                            // Initial mistake memory items for instant review demo
                            database.mistakeDao().insertMistake(
                                MistakeEntity(
                                    learnerSaid = "He go to school.",
                                    correctSentence = "He goes to school.",
                                    hindiExplanation = "He/She/It के साथ Simple Present में verb में s/es लगता है।",
                                    grammarTopic = "Simple Present (Tenses)",
                                    mistakeCount = 7,
                                    masteryScore = 40
                                )
                            )
                            database.mistakeDao().insertMistake(
                                MistakeEntity(
                                    learnerSaid = "I am living here since 2 years.",
                                    correctSentence = "I have been living here for 2 years.",
                                    hindiExplanation = "समय अवधि (Duration) के लिए 'for' और Present Perfect Continuous (have been) आता है।",
                                    grammarTopic = "Tenses & Prepositions",
                                    mistakeCount = 4,
                                    masteryScore = 60
                                )
                            )
                            database.mistakeDao().insertMistake(
                                MistakeEntity(
                                    learnerSaid = "He did not went there.",
                                    correctSentence = "He did not go there.",
                                    hindiExplanation = "Did not (Didn't) के बाद हमेशा क्रिया का पहला रूप (V1 - go) आता है।",
                                    grammarTopic = "Simple Past & Negatives",
                                    mistakeCount = 3,
                                    masteryScore = 70
                                )
                            )
                        }
                    }
                }).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
