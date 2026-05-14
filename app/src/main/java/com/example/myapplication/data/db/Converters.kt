package com.example.myapplication.data.db

import androidx.room.TypeConverter
import com.example.myapplication.data.model.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()

    // --- DieSize ---
    @TypeConverter
    fun fromDieSize(value: DieSize): String = value.name

    @TypeConverter
    fun toDieSize(value: String): DieSize = DieSize.valueOf(value)

    // --- Map<DamageType, Affinity> ---
    @TypeConverter
    fun fromAffinityMap(map: Map<DamageType, Affinity>): String {
        val stringMap = map.map { (k, v) -> k.name to v.name }.toMap()
        return gson.toJson(stringMap)
    }

    @TypeConverter
    fun toAffinityMap(json: String): Map<DamageType, Affinity> {
        val type = object : TypeToken<Map<String, String>>() {}.type
        val stringMap: Map<String, String> = gson.fromJson(json, type)
        return stringMap.map { (k, v) ->
            DamageType.valueOf(k) to Affinity.valueOf(v)
        }.toMap()
    }

    // --- List<StatusEffect> ---
    @TypeConverter
    fun fromStatusEffectList(list: List<StatusEffect>): String {
        return gson.toJson(list.map { it.name })
    }

    @TypeConverter
    fun toStatusEffectList(json: String): List<StatusEffect> {
        val type = object : TypeToken<List<String>>() {}.type
        val names: List<String> = gson.fromJson(json, type)
        return names.map { StatusEffect.valueOf(it) }
    }

    // --- List<Skill> ---
    @TypeConverter
    fun fromSkillList(list: List<Skill>): String {
        return gson.toJson(list)
    }

    @TypeConverter
    fun toSkillList(json: String): List<Skill> {
        val type = object : TypeToken<List<Skill>>() {}.type
        return gson.fromJson(json, type)
    }
}
