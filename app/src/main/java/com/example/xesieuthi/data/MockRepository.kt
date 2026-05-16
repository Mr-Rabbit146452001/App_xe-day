package com.example.xesieuthi.data

import com.example.xesieuthi.model.*
import kotlinx.coroutines.delay

object MockRepository {
    val sampleProducts = listOf(
        Product("1", "Dâu tây Đà Lạt xuất khẩu", "DAL-ST-001", "8934567890123", 125000, 500, category = "Trái cây"),
        Product("2", "Sữa tươi nguyên chất 1L", "TH-MILK-1L", "8934567890124", 32000, 1000, category = "Sữa"),
        Product("3", "Ngũ cốc Granola Organic", "GRA-ORG-500G", "8934567890125", 189000, 500, category = "Ngũ cốc"),
        Product("4", "Táo Envy New Zealand", "TAO-ENVY", "8934567890126", 250000, 1000, category = "Trái cây"),
        Product("5", "Sữa tươi TH True Milk", "TH-MILK-BOX", "8934567890127", 76000, 1000, category = "Sữa"),
        Product("6", "Bánh mì lúa mạch", "BANH-MI", "8934567890128", 45000, 200, category = "Bánh mì")
    )

    fun getMockCartItems(): List<CartItem> = listOf(
        CartItem(sampleProducts[0], 2),
        CartItem(sampleProducts[1], 3),
        CartItem(sampleProducts[2], 1)
    )

    fun getMockPaymentItems(): List<CartItem> = listOf(
        CartItem(sampleProducts[3], 1), // 3kg is represented as price or weight? User said 3kg Price 250k. Assuming price per unit if unit is kg.
        CartItem(sampleProducts[4], 2),
        CartItem(sampleProducts[5], 1)
    )

    suspend fun getProductByBarcode(barcode: String): Product? {
        delay(500)
        return sampleProducts.find { it.barcode == barcode } ?: sampleProducts.random()
    }

    suspend fun loginCustomer(qrCode: String): Customer {
        delay(800)
        return Customer("CUST001", "Nguyễn Văn A", "MEM999888")
    }

    fun getMockSensorStatus() = SensorStatus(
        currentWeightGram = 1625,
        lastDeltaWeightGram = 0,
        batteryPercent = 85,
        wifiConnected = true,
        unscannedItemDetected = false,
        cameraDetectedUnknownItem = false
    )

    fun calculateSubtotal(items: List<CartItem>): Int = items.sumOf { it.product.price * it.quantity }
    fun calculateDiscount(subtotal: Int): Int = (subtotal * 0.1).toInt()
    fun calculateVat(subtotal: Int): Int = (subtotal * 0.08).toInt()
    fun calculateTotal(subtotal: Int, discount: Int, vat: Int): Int = subtotal - discount + vat
}
