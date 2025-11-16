package com.example.mhst

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.mhst.databinding.ActivityProfileBinding
import com.example.mhst.utils.SessionManager
import com.example.mhst.UserViewModel
import java.text.SimpleDateFormat
import java.util.*

/**
 * Profile Activity - Display and manage user profile
 *
 * Concepts Applied:
 * 1. Data formatting (dates)
 * 2. User session management
 * 3. Navigation between activities
 */
class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding
    private lateinit var sessionManager: SessionManager
    private val userViewModel: UserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sessionManager = SessionManager(this)

        setupToolbar()
        loadUserProfile()
        setupButtons()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    /**
     * Load user profile information
     * Concept: LiveData observation and data formatting
     */
    private fun loadUserProfile() {
        val userId = sessionManager.getUserId()

        // Display basic info from session
        binding.tvProfileName.text = sessionManager.getUserName()
        binding.tvProfileEmail.text = sessionManager.getUserEmail()
        binding.tvUserId.text = userId.toString()

        // Load full user details from database
        userViewModel.getUserById(userId).observe(this) { user ->
            if (user != null) {
                // Format registration date
                val dateFormat = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
                val registrationDate = Date(user.registrationDate)
                binding.tvRegistrationDate.text = dateFormat.format(registrationDate)
            }
        }
    }

    /**
     * Setup button click listeners
     */
    private fun setupButtons() {
        binding.btnEditProfile.setOnClickListener {
            Toast.makeText(this, "Edit Profile - Coming Soon", Toast.LENGTH_SHORT).show()
        }

        binding.btnChangePassword.setOnClickListener {
            Toast.makeText(this, "Change Password - Coming Soon", Toast.LENGTH_SHORT).show()
        }


        }
    }

    /**
     * Show logout confirmation dialog
     */



