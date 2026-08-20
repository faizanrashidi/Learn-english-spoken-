package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface UserProgressDao {
    @Query("SELECT * FROM user_progress WHERE userId = :id LIMIT 1")
    fun getUserProgressFlow(id: String = "local_learner"): Flow<UserProgressEntity?>

    @Query("SELECT * FROM user_progress WHERE userId = :id LIMIT 1")
    suspend fun getUserProgress(id: String = "local_learner"): UserProgressEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(progress: UserProgressEntity)

    @Update
    suspend fun update(progress: UserProgressEntity)
}
