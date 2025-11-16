package com.example.mhst.database

import androidx.lifecycle.LiveData
import androidx.room.*

/**
 * DAO (Data Access Object) for User entity
 * @Dao annotation tells Room this interface handles database operations
 *
 * Concept Applied: Interface in Kotlin
 * - Defines contract for database operations
 * - Room generates implementation at compile time
 */
@Dao
interface UserDao {

    /**
     * Insert a new user
     * @Insert annotation handles INSERT SQL
     * OnConflictStrategy.REPLACE: If user exists, replace it
     * suspend: Makes this a coroutine function for async execution
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    /**
     * Get user by email and password for login
     * @Query annotation allows custom SQL queries
     * :email and :password are bound parameters
     */
    @Query("SELECT * FROM users WHERE email = :email AND password = :password LIMIT 1")
    suspend fun login(email: String, password: String): User?

    /**
     * Check if email already exists
     * Returns count of users with given email
     */
    @Query("SELECT COUNT(*) FROM users WHERE email = :email")
    suspend fun checkEmailExists(email: String): Int

    /**
     * Get user by ID
     * LiveData allows observing data changes automatically
     */
    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUserById(userId: Int): LiveData<User?>

    /**
     * Update user information
     */
    @Update
    suspend fun updateUser(user: User)

    /**
     * Delete user
     */
    @Delete
    suspend fun deleteUser(user: User)
}

/**
 * Concept Applied: Coroutines with Room
 * - suspend functions run on background threads
 * - Prevents blocking UI thread during database operations
 * - LiveData automatically updates UI when data changes
 */