package com.example.mhst

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mhst.databinding.ActivityArticleDetailBinding
import com.example.mhst.ArticleViewModel



/**
 * Article Detail Activity - Display full article
 *
 * Concepts Applied:
 * 1. Intent extras - Passing data between activities
 * 2. LiveData observation - Reactive UI updates
 * 3. Coroutines - Loading data asynchronously
 */
class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleDetailBinding
    private val articleViewModel: ArticleViewModel by viewModels()
    private var articleId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Get article ID from intent
        articleId = intent.getIntExtra("ARTICLE_ID", -1)

        if (articleId == -1) {
            finish()
            return
        }

        setupToolbar()
        loadArticle()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Article"

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    /**
     * Load and display article
     * Concept: Loading data with LiveData
     */
    private fun loadArticle() {
        articleViewModel.loadArticleById(articleId)

        articleViewModel.selectedArticle.observe(this) { article ->
            if (article != null) {
                binding.apply {
                    tvArticleTitle.text = article.title
                    tvArticleCategory.text = article.category
                    tvArticleDescription.text = article.description
                    tvArticleContent.text = article.content
                }

                supportActionBar?.title = article.title
            }
        }
    }
}