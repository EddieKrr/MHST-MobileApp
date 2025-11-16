package com.example.mhst.data.repository

import com.example.mhst.data.model.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import kotlinx.coroutines.tasks.await

class AuthRepository {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    val currentUser get() = auth.currentUser

    suspend fun login(email: String, password: String): AuthResult {
        return try {
            val result = auth.signInWithEmailAndPassword(email, password).await()
            AuthResult.Success(result.user?.uid ?: "")
        } catch (e: FirebaseAuthException) {
            AuthResult.Error(parseFirebaseError(e.errorCode))
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Login failed")
        }
    }

    suspend fun register(email: String, password: String): AuthResult {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            AuthResult.Success(result.user?.uid ?: "")
        } catch (e: FirebaseAuthException) {
            AuthResult.Error(parseFirebaseError(e.errorCode))
        } catch (e: Exception) {
            AuthResult.Error(e.message ?: "Registration failed")
        }
    }

    fun logout() = auth.signOut()

    private fun parseFirebaseError(errorCode: String): String {
        return when (errorCode) {
            "ERROR_INVALID_EMAIL" -> "Invalid email address"
            "ERROR_WRONG_PASSWORD" -> "Incorrect password"
            "ERROR_USER_NOT_FOUND" -> "No account found with this email"
            "ERROR_USER_DISABLED" -> "This account has been disabled"
            "ERROR_EMAIL_ALREADY_IN_USE" -> "Email already registered"
            "ERROR_WEAK_PASSWORD" -> "Password should be at least 6 characters"
            "ERROR_NETWORK_REQUEST_FAILED" -> "Network error. Check your connection"
            else -> "Authentication failed"
        }
    }
}