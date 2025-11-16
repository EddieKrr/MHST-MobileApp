package com.example.mhst


import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mhst.adapter.ArticleAdapter
import com.example.mhst.databinding.ActivityArticlesBinding
import com.example.mhst.ArticleViewModel
import com.google.android.material.chip.Chip

/**
 * Articles Activity - Display all articles with filtering
 *
 * Concepts Applied:
 * 1. Chip Group - Material Design filter chips
 * 2. LiveData switching - Different data sources
 * 3. RecyclerView filtering
 */
class ArticlesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticlesBinding
    private val articleViewModel: ArticleViewModel by viewModels()
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticlesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupToolbar()
        setupRecyclerView()
        setupCategoryFilters()
        observeArticles()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter { article ->
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra("ARTICLE_ID", article.articleId)
            startActivity(intent)
        }

        binding.rvArticles.apply {
            layoutManager = LinearLayoutManager(this@ArticlesActivity)
            adapter = articleAdapter
            setHasFixedSize(true)
        }
    }

    /**
     * Setup category filter chips
     * Concept: Chip selection listener
     */
    private fun setupCategoryFilters() {
        binding.chipGroupCategories.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val checkedChip = group.findViewById<Chip>(checkedIds[0])
                val category = checkedChip.text.toString()
                filterArticlesByCategory(category)
            }
        }
    }

    /**
     * Filter articles by category
     * Concept: Observing different LiveData sources
     */
    private fun filterArticlesByCategory(category: String) {
        // Remove previous observers
        articleViewModel.allArticles.removeObservers(this)

        if (category == "All") {
            observeArticles()
        } else {
            articleViewModel.getArticlesByCategory(category).observe(this) { articles ->
                articleAdapter.submitList(articles)
            }
        }
    }

    private fun observeArticles() {
        articleViewModel.allArticles.observe(this) { articles ->
            articleAdapter.submitList(articles)
        }
    }
}