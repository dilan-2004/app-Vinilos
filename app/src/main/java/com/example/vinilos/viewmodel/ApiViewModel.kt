package com.example.vinilos.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vinilos.data.model.Vinyl
import com.example.vinilos.data.repository.VinylRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ApiViewModel(
    private val repository: VinylRepository = VinylRepository()
) : ViewModel() {

    private val _vinyls = MutableStateFlow<List<Vinyl>>(emptyList())
    val vinyls: StateFlow<List<Vinyl>> = _vinyls

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadVinyls() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                _vinyls.value = repository.getProducts()
            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun createVinyl(
        name: String,
        artist: String,
        albumArt: String,
        price: Double,
        description: String
    ) {
        viewModelScope.launch {
            try {
                val newVinyl = Vinyl(
                    id = 0,
                    name = name,
                    artist = artist,
                    albumArt = albumArt,
                    price = price,
                    description = description
                )
                repository.createProduct(newVinyl)
                loadVinyls()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun updateVinyl(
        id: Int,
        name: String,
        artist: String,
        albumArt: String,
        price: Double,
        description: String
    ) {
        viewModelScope.launch {
            try {
                val updated = Vinyl(
                    id,
                    name,
                    artist,
                    albumArt,
                    price,
                    description
                )
                repository.updateProduct(id, updated)
                loadVinyls()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun deleteVinyl(id: Int) {
        viewModelScope.launch {
            try {
                repository.deleteProduct(id)
                loadVinyls()
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}

