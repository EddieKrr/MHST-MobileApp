package com.example.mhst

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mhst.database.MHSTDatabase
import com.example.mhst.database.User
import com.example.mhst.data.repository.UserRepository
import kotlinx.coroutines.launch

/**
 * ViewModel for User-related operations
 *
 * Concept Applied: MVVM Architecture
 * - ViewModel: Manages UI data and business logic
 * - Survives configuration changes (screen rotation)
 * - Communicates with Repository, not directly with database
 *
 * AndroidViewModel provides Application context
 */
class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: UserRepository

    // LiveData for observing registration result
    private val _registrationResult = MutableLiveData<RegistrationResult>()
    val registrationResult: LiveData<RegistrationResult> = _registrationResult

    // LiveData for observing login result
    private val _loginResult = MutableLiveData<LoginResult>()
    val loginResult: LiveData<LoginResult> = _loginResult

    init {
        val userDao = MHSTDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao)
    }

    /**
     * Register new user
     * viewModelScope ensures coroutine is cancelled when ViewModel is destroyed
     */
    fun registerUser(name: String, email: String, password: String) {
        viewModelScope.launch {
            val user = User(name = name, email = email, password = password)
            val result = repository.registerUser(user)

            if (result > 0) {
                _registrationResult.postValue(RegistrationResult.Success(result))
            } else {
                _registrationResult.postValue(RegistrationResult.EmailExists)
            }
        }
    }

    /**
     * Login user
     */
    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val user = repository.loginUser(email, password)

            if (user != null) {
                _loginResult.postValue(LoginResult.Success(user))
            } else {
                _loginResult.postValue(LoginResult.InvalidCredentials)
            }
        }
    }

    /**
     * Get user by ID
     */
    fun getUserById(userId: Int): LiveData<User?> {
        return repository.getUserById(userId)
    }
}

/**
 * Sealed classes for type-safe result handling
 * Concept Applied: Sealed Classes in Kotlin
 * - Restricted class hierarchies
 * - Perfect for representing result states
 */
sealed class RegistrationResult {
    data class Success(val userId: Long) : RegistrationResult()
    object EmailExists : RegistrationResult()
    data class Error(val message: String) : RegistrationResult()
}

sealed class LoginResult {
    data class Success(val user: User) : LoginResult()
    object InvalidCredentials : LoginResult()
    data class Error(val message: String) : LoginResult()
}