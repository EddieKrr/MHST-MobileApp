// File: navigation/NavGraph.kt
package com.example.mhst.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.mhst.ui.auth.LoginScreen
import com.example.mhst.ui.auth.RegisterScreen

@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String,
    onAuthSuccess: (String) -> Unit  // Takes userId parameter
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = { userId ->  // Pass the userId
                    onAuthSuccess(userId)
                },
                onNavigateToRegister = {
                    navController.navigate(Screen.Register.route)
                }
            )
        }

        composable(Screen.Register.route) {
            RegisterScreen(
                onRegisterSuccess = { userId ->
                    onAuthSuccess(userId)
                },
                onNavigateToLogin = {
                       navController.popBackStack()
                }
            )
        }
    }
}