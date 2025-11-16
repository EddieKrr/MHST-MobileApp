package com.example.mhst.data.repository

import androidx.lifecycle.LiveData
import com.example.mhst.database.User
import com.example.mhst.database.UserDao

/**
 * Repository class for User operations
 *
 * Concept Applied: Repository Pattern
 * - Mediates between data sources and ViewModels
 * - Provides clean API for data access
 * - Can combine multiple data sources (database, network, cache)
 *
 * @param userDao: Data Access Object for user operations
 */
class UserRepository(private val userDao: UserDao) {

    /**
     * Register a new user
     * Returns user ID if successful, -1 if email exists
     */
    suspend fun registerUser(user: User): Long {
        // Check if email already exists
        val emailExists = userDao.checkEmailExists(user.email) > 0

        return if (!emailExists) {
            userDao.insertUser(user)
        } else {
            -1L // Return -1 to indicate email already exists
        }
    }

    /**
     * Login user
     * Returns User object if credentials are correct, null otherwise
     */
    suspend fun loginUser(email: String, password: String): User? {
        return userDao.login(email, password)
    }

    /**
     * Get user by ID
     * Returns LiveData for automatic UI updates
     */
    fun getUserById(userId: Int): LiveData<User?> {
        return userDao.getUserById(userId)
    }

    /**
     * Update user information
     */
    suspend fun updateUser(user: User) {
        userDao.updateUser(user)
    }
}