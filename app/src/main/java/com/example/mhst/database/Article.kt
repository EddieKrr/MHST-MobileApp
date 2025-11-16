package com.example.mhst.database

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Entity for storing mental health articles
 */
@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val articleId: Int = 0,

    val title: String,
    val description: String,
    val category: String, // e.g., "Anxiety", "Depression", "BPD"
    val content: String,
    val imageResource: Int = 0 // Resource ID for article image
)