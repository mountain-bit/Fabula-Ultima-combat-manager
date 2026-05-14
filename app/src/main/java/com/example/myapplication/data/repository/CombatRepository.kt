package com.example.myapplication.data.repository

import com.example.myapplication.data.db.CombatDao
import com.example.myapplication.data.db.EnemyDao
import com.example.myapplication.data.model.Combat
import com.example.myapplication.data.model.Enemy
import kotlinx.coroutines.flow.Flow

class CombatRepository(
    private val combatDao: CombatDao,
    private val enemyDao: EnemyDao
) {
    // --- Combats ---
    fun getAllCombats(): Flow<List<Combat>> = combatDao.getAllCombats()

    suspend fun insertCombat(combat: Combat): Long = combatDao.insertCombat(combat)

    suspend fun updateCombat(combat: Combat) = combatDao.updateCombat(combat)

    suspend fun deleteCombat(combat: Combat) = combatDao.deleteCombat(combat)

    suspend fun getCombatCount(): Int = combatDao.getCombatCount()

    // --- Enemies ---
    fun getEnemiesForCombat(combatId: Long): Flow<List<Enemy>> =
        enemyDao.getEnemiesForCombat(combatId)

    suspend fun insertEnemy(enemy: Enemy): Long = enemyDao.insertEnemy(enemy)

    suspend fun updateEnemy(enemy: Enemy) = enemyDao.updateEnemy(enemy)

    suspend fun deleteEnemy(enemy: Enemy) = enemyDao.deleteEnemy(enemy)

    suspend fun getNextSortOrder(combatId: Long): Int = enemyDao.getNextSortOrder(combatId)
}
