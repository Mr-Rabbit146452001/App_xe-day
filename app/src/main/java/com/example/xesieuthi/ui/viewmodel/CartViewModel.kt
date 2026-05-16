package com.example.xesieuthi.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xesieuthi.data.MockRepository
import com.example.xesieuthi.model.CartItem
import com.example.xesieuthi.model.CartState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CartViewModel : ViewModel() {

    private val _cartState = MutableStateFlow(CartState())
    val cartState: StateFlow<CartState> = _cartState.asStateFlow()

    init {
        loadCartItems()
    }

    private fun loadCartItems() {
        viewModelScope.launch {
            // Simulate network/repository delay
            delay(500) 
            val initialItems = MockRepository.getMockCartItems()
            _cartState.update { currentState ->
                currentState.copy(items = initialItems)
            }
        }
    }

    fun updateItemQuantity(cartItem: CartItem, quantityChange: Int) {
        viewModelScope.launch {
            _cartState.update { currentState ->
                val currentItems = currentState.items.toMutableList()
                val existingItem = currentItems.find { it.product.id == cartItem.product.id }

                existingItem?.let {
                    val newQuantity = it.quantity + quantityChange
                    if (newQuantity > 0) {
                        val updatedItem = it.copy(quantity = newQuantity)
                        val index = currentItems.indexOf(it)
                        currentItems[index] = updatedItem
                    } else {
                        // Remove item if quantity becomes 0 or less
                        currentItems.remove(it)
                    }
                }
                currentState.copy(items = currentItems)
            }
        }
    }

    fun removeItem(cartItem: CartItem) {
        viewModelScope.launch {
            _cartState.update { currentState ->
                val updatedItems = currentState.items.filter { it.product.id != cartItem.product.id }
                currentState.copy(items = updatedItems)
            }
        }
    }

    fun checkout() {
        // In a real app, this would navigate to a payment screen or process the order
        viewModelScope.launch {
            // Simulate checkout process
            delay(1000) 
            // Optionally update state, e.g., show a success message or clear cart
            println("Checkout process initiated for cart: ${_cartState.value}")
            // For now, let's simulate clearing the cart after checkout
             _cartState.update { currentState ->
                currentState.copy(items = emptyList(), warningMessage = "Đơn hàng đã được xử lý!")
            }
        }
    }

    fun continueShopping() {
        // In a real app, this would navigate back to the main product screen or home screen
        println("Navigating back to shopping...")
        // No state change needed here unless you want to show a message
    }

    // Example function to simulate adding a product (can be called from another screen)
    fun addProductToCart(product: Product) {
         viewModelScope.launch {
            _cartState.update { currentState ->
                val currentItems = currentState.items.toMutableList()
                val existingItem = currentItems.find { it.product.id == product.id }

                if (existingItem != null) {
                    // Increment quantity if item already exists
                    val updatedItem = existingItem.copy(quantity = existingItem.quantity + 1)
                    val index = currentItems.indexOf(existingItem)
                    currentItems[index] = updatedItem
                } else {
                    // Add new item if it doesn't exist
                    currentItems.add(CartItem(product = product, quantity = 1))
                }
                currentState.copy(items = currentItems)
            }
        }
    }
}
