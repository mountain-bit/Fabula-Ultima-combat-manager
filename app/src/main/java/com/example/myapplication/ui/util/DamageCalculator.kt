package com.example.myapplication.ui.util

import com.example.myapplication.data.model.Affinity
import com.example.myapplication.data.model.DamageType

data class DamageResult(
    val finalDamage: Int,
    val isHealing: Boolean = false,
    val description: String = ""
)

object DamageCalculator {

    /**
     * Calculate final damage based on base damage and the target's affinity for that damage type.
     *
     * Fabula Ultima rules:
     * - Vulnerable: damage × 2
     * - Resistant: damage ÷ 2 (rounded down)
     * - Immune: 0 damage
     * - Absorb: heals for the damage amount instead
     * - None: normal damage
     */
    fun calculateDamage(baseDamage: Int, affinity: Affinity, damageType: DamageType): DamageResult {
        return when (affinity) {
            Affinity.NONE -> DamageResult(
                finalDamage = baseDamage,
                description = "${damageType.icon} $baseDamage obrażeń"
            )
            Affinity.VULNERABLE -> DamageResult(
                finalDamage = baseDamage * 2,
                description = "${damageType.icon} $baseDamage × 2 = ${baseDamage * 2} obrażeń (Wrażliwość!)"
            )
            Affinity.RESISTANT -> DamageResult(
                finalDamage = baseDamage / 2,
                description = "${damageType.icon} $baseDamage ÷ 2 = ${baseDamage / 2} obrażeń (Odporność)"
            )
            Affinity.IMMUNE -> DamageResult(
                finalDamage = 0,
                description = "${damageType.icon} Immunitet! 0 obrażeń"
            )
            Affinity.ABSORB -> DamageResult(
                finalDamage = baseDamage,
                isHealing = true,
                description = "${damageType.icon} Absorpcja! +$baseDamage HP"
            )
        }
    }
}
