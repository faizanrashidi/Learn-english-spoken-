package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface MistakeDao {
    @Query("SELECT * FROM mistakes ORDER BY mistakeCount DESC, lastPracticedTimestamp DESC")
    fun getAllMistakesFlow(): Flow<List<MistakeEntity>>

    @Query("SELECT * FROM mistakes WHERE grammarTopic = :topic")
    suspend fun getMistakesByTopic(topic: String): List<MistakeEntity>

    @Query("SELECT * FROM mistakes WHERE correctSentence = :correct LIMIT 1")
    suspend fun findByCorrectSentence(correct: String): MistakeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMistake(mistake: MistakeEntity): Long

    @Update
    suspend fun updateMistake(mistake: MistakeEntity)

    @Query("DELETE FROM mistakes WHERE id = :id")
    suspend fun deleteMistakeById(id: Long)

    @Query("DELETE FROM mistakes")
    suspend fun clearAllMistakes()
}
