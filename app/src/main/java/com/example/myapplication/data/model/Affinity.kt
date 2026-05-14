package com.example.myapplication.data.model

import androidx.compose.ui.graphics.Color

enum class Affinity(val displayName: String, val shortName: String, val colorHex: Long) {
    NONE("Brak", "—", 0xFF9CA3AF),          // gray
    VULNERABLE("Wrażliwość", "VU", 0xFFF59E0B),  // amber/orange
    RESISTANT("Odporność", "RS", 0xFF6366F1),     // indigo
    IMMUNE("Immunitet", "IM", 0xFF6B7280),        // dark gray
    ABSORB("Absorpcja", "AB", 0xFF10B981);        // emerald

    val color: Color get() = Color(colorHex)

    fun next(): Affinity {
        val values = entries
        return values[(ordinal + 1) % values.size]
    }
}
