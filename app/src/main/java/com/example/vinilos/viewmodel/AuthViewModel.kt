package com.example.vinilos.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilos.data.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val repository: AuthRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    fun login(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = repository.login(email, password)

            if (result.isSuccess) {
                onSuccess()
            } else {
                _error.value = result.exceptionOrNull()?.message
            }

            _loading.value = false
        }
    }

    fun register(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = repository.register(email, password)

            if (result.isSuccess) {
                onSuccess()
            } else {
                _error.value = result.exceptionOrNull()?.message
            }

            _loading.value = false
        }
    }

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch {
            repository.logout()
            onLoggedOut()
        }
    }
}