package com.example.mhst

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.mhst.data.repository.AuthRepository
import com.example.mhst.navigation.NavGraph
import com.example.mhst.navigation.Screen
import com.example.mhst.ui.theme.MHSTFirebaseTheme
import com.example.mhst.utils.SessionManager

/**
 * AuthActivity - Firebase Authentication Entry Point
 * Uses Jetpack Compose for modern UI
 */
class AuthActivity : ComponentActivity() {

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sessionManager = SessionManager(this)

        // Check if user is already logged in (Firebase)
        val authRepository = AuthRepository()
        if (authRepository.currentUser != null) {
            navigateToMain()
            return
        }

        setContent {
            MHSTFirebaseTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()

                    NavGraph(  // Changed from AuthNavGraph
                        navController = navController,
                        startDestination = Screen.Login.route,  // Changed from AuthScreen
                        onAuthSuccess = { userId ->
                            // Save to session and navigate to MainActivity
                            saveUserSession(userId)
                            navigateToMain()
                        }
                    )
                }
            }
        }
    }

    private fun saveUserSession(userId: String) {
        val authRepository = AuthRepository()
        val user = authRepository.currentUser

        if (user != null) {
            
            sessionManager.saveUserSession(
                userId = userId.hashCode(), // Convert Firebase UID to Int
                name = user.email?.substringBefore("@") ?: "User",
                email = user.email ?: ""
            )
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}