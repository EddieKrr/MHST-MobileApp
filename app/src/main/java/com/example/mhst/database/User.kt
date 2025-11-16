package com.example.mhst.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity class representing the Users table in Room database
 * @Entity annotation marks this as a database table
 * tableName specifies the actual table name in SQLite
 */
@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val userId: Int = 0,

    val name: String,
    val email: String,
    val password: String,
    val registrationDate: Long = System.currentTimeMillis()
)

/**
 * Concept Applied: Kotlin Data Classes
 * - Automatically generates equals(), hashCode(), toString(), copy()
 * - Perfect for representing database entities
 *
 * Room Annotations:
 * - @Entity: Marks class as database table
 * - @PrimaryKey: Defines primary key, autoGenerate creates auto-increment IDs
 */