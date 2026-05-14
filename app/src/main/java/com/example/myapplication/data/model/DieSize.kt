package com.example.myapplication.data.model

enum class DieSize(val sides: Int, val displayName: String) {
    D6(6, "d6"),
    D8(8, "d8"),
    D10(10, "d10"),
    D12(12, "d12");

    /**
     * Reduce die by [steps] sizes. Minimum is D6.
     * E.g. D10.stepDown(1) = D8, D8.stepDown(2) = D6
     */
    fun stepDown(steps: Int = 1): DieSize {
        val newOrdinal = (ordinal - steps).coerceAtLeast(0)
        return entries[newOrdinal]
    }

    /**
     * Increase die by [steps] sizes. Maximum is D12.
     */
    fun stepUp(steps: Int = 1): DieSize {
        val newOrdinal = (ordinal + steps).coerceAtMost(entries.size - 1)
        return entries[newOrdinal]
    }
}
