package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "enemies",
    foreignKeys = [
        ForeignKey(
            entity = Combat::class,
            parentColumns = ["id"],
            childColumns = ["combatId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("combatId")]
)
data class Enemy(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val combatId: Long,
    val name: String = "Nowy Wróg",
    val maxHp: Int = 40,
    val currentHp: Int = 40,
    val maxMp: Int = 30,
    val currentMp: Int = 30,

    // Base dice for each attribute
    val dexDie: DieSize = DieSize.D8,
    val insDie: DieSize = DieSize.D8,
    val migDie: DieSize = DieSize.D8,
    val wlpDie: DieSize = DieSize.D8,

    // Affinities stored as JSON map via TypeConverter
    val affinities: Map<DamageType, Affinity> = DamageType.entries.associateWith { Affinity.NONE },

    // Active status effects stored as JSON list via TypeConverter
    val statusEffects: List<StatusEffect> = emptyList(),

    // Skills stored as JSON list via TypeConverter
    val skills: List<Skill> = emptyList(),

    val sortOrder: Int = 0
) {
    /**
     * Get effective die size for an attribute, accounting for active status effects.
     * Status penalties stack: each status that affects the attribute reduces the die by 1 step.
     */
    fun getEffectiveDie(attribute: Attribute): DieSize {
        val baseDie = when (attribute) {
            Attribute.DEX -> dexDie
            Attribute.INS -> insDie
            Attribute.MIG -> migDie
            Attribute.WLP -> wlpDie
        }
        val penalties = statusEffects.count { attribute in it.affectedAttributes }
        return baseDie.stepDown(penalties)
    }

    /**
     * Check if a die has been modified by status effects.
     * Returns: -1 if reduced, 0 if unchanged, 1 if buffed (future use)
     */
    fun getDieModification(attribute: Attribute): Int {
        val baseDie = when (attribute) {
            Attribute.DEX -> dexDie
            Attribute.INS -> insDie
            Attribute.MIG -> migDie
            Attribute.WLP -> wlpDie
        }
        val effectiveDie = getEffectiveDie(attribute)
        return when {
            effectiveDie.ordinal < baseDie.ordinal -> -1
            effectiveDie.ordinal > baseDie.ordinal -> 1
            else -> 0
        }
    }

    val isInCrisis: Boolean get() = currentHp <= maxHp / 2
}
