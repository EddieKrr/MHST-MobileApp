package com.example.mhst.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article): Long

    /**
     * Get all articles
     * LiveData ensures UI updates automatically when articles change
     */
    @Query("SELECT * FROM articles ORDER BY articleId DESC")
    fun getAllArticles(): LiveData<List<Article>>

    /**
     * Get articles by category
     */
    @Query("SELECT * FROM articles WHERE category = :category ORDER BY articleId DESC")
    fun getArticlesByCategory(category: String): LiveData<List<Article>>

    /**
     * Get single article by ID
     */
    @Query("SELECT * FROM articles WHERE articleId = :articleId")
    suspend fun getArticleById(articleId: Int): Article?

    @Update
    suspend fun updateArticle(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)

    /**
     * Insert multiple articles at once
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticles(articles: List<Article>)
}