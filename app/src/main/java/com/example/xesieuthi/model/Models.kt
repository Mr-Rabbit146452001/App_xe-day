package com.example.xesieuthi.model

import androidx.compose.runtime.Immutable

@Immutable
data class Product(
    val id: String,
    val name: String,
    val sku: String = "",
    val barcode: String,
    val price: Int,
    val weightGram: Int,
    val imageUrl: String? = null,
    val category: String = "General"
)

@Immutable
data class CartItem(
    val product: Product,
    val quantity: Int
)

@Immutable
data class Customer(
    val id: String,
    val name: String,
    val memberCode: String
)

@Immutable
data class CartState(
    val items: List<CartItem> = emptyList(),
    val warningMessage: String? = null,
    val isCustomerLoggedIn: Boolean = false,
    val customer: Customer? = null
) {
    val totalPrice: Int get() = items.sumOf { it.product.price * it.quantity }
    val totalWeightGram: Int get() = items.sumOf { it.product.weightGram * it.quantity }
    val totalItems: Int get() = items.sumOf { it.quantity }
}

@Immutable
data class SensorStatus(
    val currentWeightGram: Int = 0,
    val lastDeltaWeightGram: Int = 0,
    val irDirection: String = "NONE", // IN, OUT, NONE
    val unscannedItemDetected: Boolean = false,
    val cameraDetectedUnknownItem: Boolean = false,
    val wifiConnected: Boolean = true,
    val batteryPercent: Int = 85
)

enum class PaymentMethod {
    QR_BANKING,
    E_WALLET,
    MEMBER_CARD,
    CASHIER
}

data class Receipt(
    val receiptId: String,
    val orderId: String,
    val time: String,
    val storeName: String,
    val cartCode: String,
    val subtotal: Int,
    val discount: Int,
    val vat: Int,
    val total: Int
)

data class SupportRequest(
    val cartId: String,
    val reason: String,
    val message: String,
    val createdAt: String
)

enum class PaymentStatus {
    WAITING,
    PAID,
    FAILED,
    EXPIRED
}

enum class ConnectionStatus {
    ONLINE,
    OFFLINE,
    SYNCING,
    ERROR
}
