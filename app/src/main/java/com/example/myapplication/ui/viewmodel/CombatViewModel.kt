package com.example.myapplication.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.db.AppDatabase
import com.example.myapplication.data.model.*
import com.example.myapplication.data.repository.CombatRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class CombatViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: CombatRepository

    init {
        val db = AppDatabase.getDatabase(application)
        repository = CombatRepository(db.combatDao(), db.enemyDao())
    }

    // --- State ---
    val combats: StateFlow<List<Combat>> = repository.getAllCombats()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _activeCombatId = MutableStateFlow<Long?>(null)
    val activeCombatId: StateFlow<Long?> = _activeCombatId.asStateFlow()

    val enemies: StateFlow<List<Enemy>> = _activeCombatId
        .flatMapLatest { combatId ->
            if (combatId != null) {
                repository.getEnemiesForCombat(combatId)
            } else {
                flowOf(emptyList())
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Auto-select first combat when combats change
    init {
        viewModelScope.launch {
            combats.collect { combatList ->
                if (_activeCombatId.value == null && combatList.isNotEmpty()) {
                    _activeCombatId.value = combatList.first().id
                }
                // If active combat was deleted, select the first one
                if (combatList.isNotEmpty() && combatList.none { it.id == _activeCombatId.value }) {
                    _activeCombatId.value = combatList.first().id
                }
            }
        }

        // Create default combat if none exists
        viewModelScope.launch {
            if (repository.getCombatCount() == 0) {
                repository.insertCombat(Combat(name = "W1"))
            }
        }
    }

    // --- Combat Actions ---
    fun selectCombat(combatId: Long) {
        _activeCombatId.value = combatId
    }

    fun addCombat() {
        viewModelScope.launch {
            val count = repository.getCombatCount()
            val combat = Combat(name = "W${count + 1}")
            val id = repository.insertCombat(combat)
            _activeCombatId.value = id
        }
    }

    fun deleteCombat(combat: Combat) {
        viewModelScope.launch {
            repository.deleteCombat(combat)
        }
    }

    // --- Enemy Actions ---
    fun addEnemy(
        name: String = "Nowy Wróg",
        maxHp: Int = 40,
        maxMp: Int = 30,
        dexDie: DieSize = DieSize.D8,
        insDie: DieSize = DieSize.D8,
        migDie: DieSize = DieSize.D8,
        wlpDie: DieSize = DieSize.D8
    ) {
        val combatId = _activeCombatId.value ?: return
        viewModelScope.launch {
            val sortOrder = repository.getNextSortOrder(combatId)
            val enemy = Enemy(
                combatId = combatId,
                name = name,
                maxHp = maxHp,
                currentHp = maxHp,
                maxMp = maxMp,
                currentMp = maxMp,
                dexDie = dexDie,
                insDie = insDie,
                migDie = migDie,
                wlpDie = wlpDie,
                sortOrder = sortOrder
            )
            repository.insertEnemy(enemy)
        }
    }

    fun deleteEnemy(enemy: Enemy) {
        viewModelScope.launch {
            repository.deleteEnemy(enemy)
        }
    }

    fun updateEnemy(enemy: Enemy) {
        viewModelScope.launch {
            repository.updateEnemy(enemy)
        }
    }

    // --- HP/MP Quick Actions ---
    fun adjustHp(enemy: Enemy, amount: Int) {
        val newHp = (enemy.currentHp + amount).coerceIn(0, enemy.maxHp)
        updateEnemy(enemy.copy(currentHp = newHp))
    }

    fun adjustMp(enemy: Enemy, amount: Int) {
        val newMp = (enemy.currentMp + amount).coerceIn(0, enemy.maxMp)
        updateEnemy(enemy.copy(currentMp = newMp))
    }

    // --- Damage Application ---
    fun applyDamage(enemy: Enemy, baseDamage: Int, damageType: DamageType) {
        val affinity = enemy.affinities[damageType] ?: Affinity.NONE
        val result = com.example.myapplication.ui.util.DamageCalculator.calculateDamage(
            baseDamage, affinity, damageType
        )

        if (result.isHealing) {
            adjustHp(enemy, result.finalDamage)
        } else {
            adjustHp(enemy, -result.finalDamage)
        }
    }

    // --- Status Effects ---
    fun toggleStatusEffect(enemy: Enemy, status: StatusEffect) {
        val newStatuses = if (status in enemy.statusEffects) {
            enemy.statusEffects - status
        } else {
            enemy.statusEffects + status
        }
        updateEnemy(enemy.copy(statusEffects = newStatuses))
    }

    // --- Affinities ---
    fun cycleAffinity(enemy: Enemy, damageType: DamageType) {
        val currentAffinity = enemy.affinities[damageType] ?: Affinity.NONE
        val newAffinities = enemy.affinities.toMutableMap()
        newAffinities[damageType] = currentAffinity.next()
        updateEnemy(enemy.copy(affinities = newAffinities))
    }

    fun setAffinity(enemy: Enemy, damageType: DamageType, affinity: Affinity) {
        val newAffinities = enemy.affinities.toMutableMap()
        newAffinities[damageType] = affinity
        updateEnemy(enemy.copy(affinities = newAffinities))
    }

    // --- Skills ---
    fun addSkill(enemy: Enemy, skill: Skill) {
        val newSkills = enemy.skills + skill
        updateEnemy(enemy.copy(skills = newSkills))
    }

    fun removeSkill(enemy: Enemy, index: Int) {
        val newSkills = enemy.skills.toMutableList().apply { removeAt(index) }
        updateEnemy(enemy.copy(skills = newSkills))
    }

    fun updateSkill(enemy: Enemy, index: Int, skill: Skill) {
        val newSkills = enemy.skills.toMutableList().apply { set(index, skill) }
        updateEnemy(enemy.copy(skills = newSkills))
    }

    // --- Enemy name ---
    fun renameEnemy(enemy: Enemy, newName: String) {
        updateEnemy(enemy.copy(name = newName))
    }
}
