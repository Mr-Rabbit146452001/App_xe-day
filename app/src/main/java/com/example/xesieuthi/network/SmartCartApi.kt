package com.example.xesieuthi.network

import com.example.xesieuthi.model.*

/**
 * Interface defining the backend and hardware communication.
 * TODO: Implement with Retrofit, Ktor, or WebSocket for real hardware/backend connection.
 */
interface SmartCartApi {
    // Customer / Auth
    suspend fun loginByQr(qrCode: String): Customer
    
    // Product / Catalog
    suspend fun getProductByBarcode(barcode: String): Product
    
    // Cart Management (Backend side)
    suspend fun addCartItem(cartId: String, productId: String, quantity: Int)
    suspend fun removeCartItem(cartId: String, productId: String, quantity: Int)
    suspend fun getCart(cartId: String): CartState
    
    // Payment
    suspend fun createPayment(cartId: String): String // Returns payment/order ID or QR link
    
    // Hardware / Sensors
    suspend fun getCartSensorStatus(): SensorStatus
}
