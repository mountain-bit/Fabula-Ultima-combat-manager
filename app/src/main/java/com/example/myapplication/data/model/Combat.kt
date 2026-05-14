package com.example.myapplication.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "combats")
data class Combat(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String = "W1",
    val createdAt: Long = System.currentTimeMillis()
)
