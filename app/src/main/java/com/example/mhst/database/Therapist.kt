package com.example.mhst.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "therapists")
data class Therapist(
    @PrimaryKey(autoGenerate = true)
    val therapistId: Int = 0,
    val name: String,
    val specialization: String,
    val phone: String,
    val email: String,
    val location: String,
    val availability: String,
    val imageUrl: String? = null
)
