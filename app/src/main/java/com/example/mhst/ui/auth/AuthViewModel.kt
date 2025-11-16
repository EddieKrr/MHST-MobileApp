package com.example.mhst.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mhst.data.model.AuthResult
import com.example.mhst.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository = AuthRepository()
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthResult>(AuthResult.Idle)
    val authState: StateFlow<AuthResult> = _authState.asStateFlow()

    fun login(email: String, password: String) {
        if (!validateInput(email, password)) return

        viewModelScope.launch {
            _authState.value = AuthResult.Loading
            _authState.value = repository.login(email, password)
        }
    }

    fun register(email: String, password: String) {
        if (!validateInput(email, password)) return

        viewModelScope.launch {
            _authState.value = AuthResult.Loading
            _authState.value = repository.register(email, password)
        }
    }

    fun resetState() {
        _authState.value = AuthResult.Idle
    }

    private fun validateInput(email: String, password: String): Boolean {
        return when {
            email.isBlank() -> {
                _authState.value = AuthResult.Error("Email cannot be empty")
                false
            }
            password.isBlank() -> {
                _authState.value = AuthResult.Error("Password cannot be empty")
                false
            }
            password.length < 6 -> {
                _authState.value = AuthResult.Error("Password must be at least 6 characters")
                false
            }
            else -> true
        }
    }
}