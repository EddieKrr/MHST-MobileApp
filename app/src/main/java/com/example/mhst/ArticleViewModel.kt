package com.example.mhst

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mhst.database.Article
import com.example.mhst.database.MHSTDatabase
import com.example.mhst.data.repository.ArticleRepository
import kotlinx.coroutines.launch

class ArticleViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: ArticleRepository

    // LiveData for all articles
    val allArticles: LiveData<List<Article>>

    // LiveData for selected article
    private val _selectedArticle = MutableLiveData<Article?>()
    val selectedArticle: LiveData<Article?> = _selectedArticle

    init {
        val articleDao = MHSTDatabase.getDatabase(application).articleDao()
        repository = ArticleRepository(articleDao)
        allArticles = repository.getAllArticles()
    }

    /**
     * Get articles by category
     */
    fun getArticlesByCategory(category: String): LiveData<List<Article>> {
        return repository.getArticlesByCategory(category)
    }

    /**
     * Load article by ID
     */
    fun loadArticleById(articleId: Int) {
        viewModelScope.launch {
            val article = repository.getArticleById(articleId)
            _selectedArticle.postValue(article)
        }
    }
}