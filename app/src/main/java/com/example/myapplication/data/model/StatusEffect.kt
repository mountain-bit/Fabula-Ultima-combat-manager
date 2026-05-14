package com.example.myapplication.data.model

/**
 * Fabula Ultima status effects. Each reduces one or more attribute dice by one step.
 * Effects are cumulative and minimum die size is always D6.
 */
enum class StatusEffect(
    val displayName: String,
    val affectedAttributes: Set<Attribute>,
    val icon: String
) {
    DAZED("Dazed", setOf(Attribute.INS), "💫"),
    ENRAGED("Enraged", setOf(Attribute.DEX, Attribute.INS), "😡"),
    POISONED("Poisoned", setOf(Attribute.MIG, Attribute.WLP), "🤢"),
    SHAKEN("Shaken", setOf(Attribute.WLP), "😰"),
    SLOW("Slow", setOf(Attribute.DEX), "🐌"),
    WEAK("Weak", setOf(Attribute.MIG), "😩");
}

enum class Attribute(val displayName: String, val shortName: String) {
    DEX("Dexterity", "DEX"),
    INS("Insight", "INS"),
    MIG("Might", "MIG"),
    WLP("Willpower", "WLP");
}
