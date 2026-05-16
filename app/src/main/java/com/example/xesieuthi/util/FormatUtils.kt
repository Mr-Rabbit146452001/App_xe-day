package com.example.xesieuthi.util

import java.text.NumberFormat
import java.util.Locale

object FormatUtils {
    fun formatCurrency(amount: Int): String {
        val formatter = NumberFormat.getCurrencyInstance(Locale("vi", "VN"))
        return formatter.format(amount).replace("₫", "đ")
    }
}
