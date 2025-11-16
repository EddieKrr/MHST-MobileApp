package com.example.mhst.navigation

sealed class Screen(val route: String) {
    object Login : Screen ("Login")
    object Register : Screen ("Register")
    object Home : Screen("Home")
}