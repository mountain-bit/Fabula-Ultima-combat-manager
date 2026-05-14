package com.example.myapplication.data.model

enum class DamageType(val displayName: String, val icon: String, val shortName: String) {
    PHYSICAL("Physical", "⚔️", "PHY"),
    FIRE("Fire", "🔥", "FIR"),
    ICE("Ice", "❄️", "ICE"),
    BOLT("Bolt", "⚡", "BLT"),
    EARTH("Earth", "🌍", "ERT"),
    AIR("Air", "💨", "AIR"),
    LIGHT("Light", "✨", "LGT"),
    DARK("Dark", "🌑", "DRK"),
    POISON("Poison", "☠️", "PSN");
}
