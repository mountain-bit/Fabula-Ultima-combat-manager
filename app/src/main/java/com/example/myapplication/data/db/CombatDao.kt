package com.example.myapplication.data.db

import androidx.room.*
import com.example.myapplication.data.model.Combat
import kotlinx.coroutines.flow.Flow

@Dao
interface CombatDao {
    @Query("SELECT * FROM combats ORDER BY createdAt ASC")
    fun getAllCombats(): Flow<List<Combat>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCombat(combat: Combat): Long

    @Update
    suspend fun updateCombat(combat: Combat)

    @Delete
    suspend fun deleteCombat(combat: Combat)

    @Query("SELECT COUNT(*) FROM combats")
    suspend fun getCombatCount(): Int
}
