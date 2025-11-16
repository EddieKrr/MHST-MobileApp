package com.example.mhst.data.repository

import androidx.lifecycle.LiveData
import com.example.mhst.database.Article
import com.example.mhst.database.ArticleDao

/**
 * Repository for Article operations
 */
class ArticleRepository(private val articleDao: ArticleDao) {

    /**
     * Get all articles
     * LiveData automatically updates when database changes
     */
    fun getAllArticles(): LiveData<List<Article>> {
        return articleDao.getAllArticles()
    }

    /**
     * Get articles filtered by category
     */
    fun getArticlesByCategory(category: String): LiveData<List<Article>> {
        return articleDao.getArticlesByCategory(category)
    }

    /**
     * Get single article by ID
     */
    suspend fun getArticleById(articleId: Int): Article? {
        return articleDao.getArticleById(articleId)
    }

    /**
     * Add new article
     */
    suspend fun insertArticle(article: Article): Long {
        return articleDao.insertArticle(article)
    }
}