package com.example.myapplication.data.db

import androidx.room.*
import com.example.myapplication.data.model.Enemy
import kotlinx.coroutines.flow.Flow

@Dao
interface EnemyDao {
    @Query("SELECT * FROM enemies WHERE combatId = :combatId ORDER BY sortOrder ASC")
    fun getEnemiesForCombat(combatId: Long): Flow<List<Enemy>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEnemy(enemy: Enemy): Long

    @Update
    suspend fun updateEnemy(enemy: Enemy)

    @Delete
    suspend fun deleteEnemy(enemy: Enemy)

    @Query("DELETE FROM enemies WHERE combatId = :combatId")
    suspend fun deleteAllEnemiesForCombat(combatId: Long)

    @Query("SELECT COALESCE(MAX(sortOrder), 0) + 1 FROM enemies WHERE combatId = :combatId")
    suspend fun getNextSortOrder(combatId: Long): Int
}
