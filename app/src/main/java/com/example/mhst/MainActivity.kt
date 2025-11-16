package com.example.mhst

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mhst.adapter.ArticleAdapter
import com.example.mhst.databinding.ActivityMainBinding
import com.example.mhst.utils.SessionManager
import com.example.mhst.ArticleViewModel
import com.google.android.material.navigation.NavigationView

/**
 * Main Activity - Home Screen
 *
 * Concepts Applied:
 * 1. DrawerLayout - Side navigation menu
 * 2. RecyclerView - Efficient list display
 * 3. ViewModels & LiveData - Reactive UI
 * 4. Navigation Component pattern
 */
class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sessionManager: SessionManager
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private lateinit var articleAdapter: ArticleAdapter
    private val articleViewModel: ArticleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)




        setupToolbar()
        setupDrawer()
        setupRecyclerView()
        setupUserInfo()
        observeArticles()
        setupSocialButtons()
    }

    /**
     * Set up toolbar
     */
    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "MHST"
    }

    /**
     * Set up navigation drawer
     * Concept: Navigation Drawer Pattern
     */
    private fun setupDrawer() {
        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.app_name,
            R.string.app_name
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        binding.navigationView.setNavigationItemSelectedListener(this)

        // Update navigation header
        val headerView = binding.navigationView.getHeaderView(0)
        val tvHeaderName = headerView.findViewById<TextView>(R.id.tvHeaderName)
        val tvHeaderEmail = headerView.findViewById<TextView>(R.id.tvHeaderEmail)

        tvHeaderName.text = sessionManager.getUserName()
        tvHeaderEmail.text = sessionManager.getUserEmail()
    }

    /**
     * Set up RecyclerView for articles
     * Concept: RecyclerView with LinearLayoutManager
     */
    private fun setupRecyclerView() {
        articleAdapter = ArticleAdapter { article ->
            // Navigate to article detail
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra("ARTICLE_ID", article.articleId)
            startActivity(intent)
        }

        binding.rvArticles.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = articleAdapter
            setHasFixedSize(true)
        }
    }

    /**
     * Display user welcome message
     */
    private fun setupUserInfo() {
        val userName = sessionManager.getUserName()
        binding.tvWelcome.text = "You'll love it here $userName"
    }

    /**
     * Observe articles from ViewModel
     * Concept: LiveData observation
     */
    private fun observeArticles() {
        articleViewModel.allArticles.observe(this) { articles ->
            if (articles != null) {
                articleAdapter.submitList(articles)
            }
        }
    }

    /**
     * Set up social media buttons
     */
    private fun setupSocialButtons() {
        binding.btnInstagram.setOnClickListener {
            Toast.makeText(this, "Instagram clicked", Toast.LENGTH_SHORT).show()
        }

        binding.btnTwitter.setOnClickListener {
            Toast.makeText(this, "Twitter clicked", Toast.LENGTH_SHORT).show()
        }

        binding.btnLinkedIn.setOnClickListener {
            Toast.makeText(this, "LinkedIn clicked", Toast.LENGTH_SHORT).show()
        }

        binding.btnTikTok.setOnClickListener {
            Toast.makeText(this, "TikTok clicked", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * Handle navigation item clicks
     */
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_home -> {
                // Already on home
            }
            R.id.nav_articles -> {
                val intent = Intent(this, ArticlesActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_therapists -> {
                val intent = Intent(this, TherapistsActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_profile -> {
                val intent = Intent(this, ProfileActivity::class.java)
                startActivity(intent)
            }
            R.id.nav_logout -> {
                showLogoutDialog()
            }
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     * Show logout confirmation dialog
     * Concept: AlertDialog
     */
    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Logout")
            .setMessage("Are you sure you want to logout?")
            .setPositiveButton("Yes") { _, _ ->
                sessionManager.clearSession()

                com.example.mhst.data.repository.AuthRepository().logout()

                val intent = Intent(this, AuthActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("No", null)
            .show()
    }

    /**
     * Navigate to Sign In activity
     */


    /**
     * Handle back button press
     */
    @SuppressLint("GestureBackNavigation")
    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
}

/**
 * Key Concepts Demonstrated:
 * 1. DrawerLayout - Navigation drawer
 * 2. RecyclerView - Efficient list
 * 3. LiveData - Reactive data
 * 4. AlertDialog - User confirmation
 * 5. Intent navigation - Screen transitions
 */