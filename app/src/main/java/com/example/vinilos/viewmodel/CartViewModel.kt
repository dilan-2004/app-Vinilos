package com.example.vinilos.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import com.example.vinilos.data.model.CartItem
import com.example.vinilos.data.model.Vinyl

class CartViewModel : ViewModel() {

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    fun addToCart(vinyl: Vinyl) {
        _cartItems.update { current ->
            val existing = current.find { it.vinyl.id == vinyl.id }

            if (existing != null) {
                current.map {
                    if ( it.vinyl.id == vinyl.id)
                        it.copy(quantity = it.quantity + 1)
                    else it
                }
            } else {
                current + CartItem(vinyl)
            }
        }
    }

    fun removeFromCart(id: Int) {
        _cartItems.update { current ->
            current.filterNot { it.vinyl.id == id}
        }
    }

    fun clearCart() {
        _cartItems.value = emptyList()
    }

    fun totalPrice(): Double {
        return _cartItems.value.sumOf {
            it.vinyl.price * it.quantity
        }
    }
}