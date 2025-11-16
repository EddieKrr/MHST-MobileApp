package com.example.mhst.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mhst.database.Article
import com.example.mhst.databinding.ItemArticleBinding

/**
 * RecyclerView Adapter for displaying articles
 *
 * Concepts Applied:
 * 1. RecyclerView Pattern - Efficient list display
 * 2. ViewHolder Pattern - Reuses views for performance
 * 3. ListAdapter - Automatic diff calculations
 * 4. Higher-order functions - onClick lambda
 */
class ArticleAdapter(
    private val onArticleClick: (Article) -> Unit
) : ListAdapter<Article, ArticleAdapter.ArticleViewHolder>(ArticleDiffCallback()) {

    /**
     * ViewHolder class holds references to views
     * Concept: View Binding in ViewHolder
     */
    inner class ArticleViewHolder(
        private val binding: ItemArticleBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.apply {
                tvArticleTitle.text = article.title
                tvArticleCategory.text = article.category
                tvArticleDescription.text = article.description

                // Set click listener
                root.setOnClickListener {
                    onArticleClick(article)
                }
            }
        }
    }

    /**
     * Create new ViewHolder
     * Called when RecyclerView needs a new ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val binding = ItemArticleBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ArticleViewHolder(binding)
    }

    /**
     * Bind data to ViewHolder
     * Called to display data at specified position
     */
    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = getItem(position)
        holder.bind(article)
    }
}

/**
 * DiffUtil Callback for efficient list updates
 * Concept: DiffUtil calculates minimal changes between lists
 */
class ArticleDiffCallback : DiffUtil.ItemCallback<Article>() {

    /**
     * Check if two items represent the same article
     */
    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem.articleId == newItem.articleId
    }

    /**
     * Check if item contents are the same
     */
    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
        return oldItem == newItem
    }
}

/**
 * Key Concepts:
 * 1. RecyclerView - Efficient large list display
 * 2. ViewHolder - Caches view references
 * 3. ListAdapter - Handles list updates automatically
 * 4. DiffUtil - Calculates minimal UI updates
 * 5. Lambda functions - Callback for clicks
 */
