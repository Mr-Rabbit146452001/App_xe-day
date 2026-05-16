package com.example.xesieuthi.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xesieuthi.ui.theme.BluePrimary
import kotlinx.coroutines.delay

@Composable
fun PaymentQrScreen(onPaymentSuccess: () -> Unit, onCancel: () -> Unit) {
    var timeLeft by remember { mutableIntStateOf(300) } // 5 minutes
    val isWaitingPayment by remember { mutableStateOf(true) }

    LaunchedEffect(key1 = timeLeft) {
        if (timeLeft > 0) {
            delay(1000L)
            timeLeft -= 1
        }
    }

    val minutes = timeLeft / 60
    val seconds = timeLeft % 60
    val timeString = String.format("%02d:%02d", minutes, seconds)

    Scaffold(
        bottomBar = {
            Surface(
                tonalElevation = 8.dp,
                shadowElevation = 8.dp,
                color = Color.White
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = onCancel,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Red),
                        border = BorderStroke(1.dp, Color.Red)
                    ) {
                        Text("Hủy thanh toán")
                    }
                    Button(
                        onClick = onPaymentSuccess,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                    ) {
                        Text("Tôi đã thanh toán")
                    }
                }
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = BluePrimary)
                            Spacer(Modifier.width(8.dp))
                            Text("Tóm tắt đơn hàng", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        }
                        Spacer(Modifier.height(12.dp))
                        SummaryItem("Táo Envy Mỹ - 3kg", "120.000đ")
                        SummaryItem("Sữa TH True Milk - 2 thùng", "450.000đ")
                        Text(
                            "Và 12 sản phẩm khác...",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                        PaymentSummaryRow("Tạm tính", "2.450.000 VNĐ")
                        PaymentSummaryRow("Khuyến mãi", "0 VNĐ")
                        PaymentSummaryRow("Tổng thanh toán", "2.450.000 VNĐ", isTotal = true)
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "Quét mã để thanh toán",
                            color = BluePrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            "Vui lòng sử dụng ứng dụng Ngân hàng hoặc Ví điện tử để quét mã.",
                            style = MaterialTheme.typography.bodyMedium,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )

                        Spacer(Modifier.height(16.dp))

                        // QR Placeholder
                        Box(
                            modifier = Modifier
                                .size(200.dp)
                                .border(1.dp, Color.LightGray, RoundedCornerShape(8.dp))
                                .background(Color(0xFFF5F5F5), RoundedCornerShape(8.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.QrCodeScanner,
                                contentDescription = "QR Code",
                                modifier = Modifier.size(100.dp),
                                tint = Color.Gray
                            )
                            // TODO: lấy QR thanh toán từ backend createPayment(cartId)
                        }

                        Spacer(Modifier.height(16.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(16.dp),
                                strokeWidth = 2.dp,
                                color = BluePrimary
                            )
                            Spacer(Modifier.width(8.dp))
                            Text("Đang chờ thanh toán...", color = Color.Gray)
                        }

                        Spacer(Modifier.height(8.dp))

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AccessTime, contentDescription = null, tint = Color.Red, modifier = Modifier.size(20.dp))
                            Spacer(Modifier.width(4.dp))
                            Text(timeString, color = Color.Red, fontWeight = FontWeight.Bold, fontSize = 20.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SummaryItem(name: String, price: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(name, modifier = Modifier.weight(1f))
        Text(price, fontWeight = FontWeight.Medium)
    }
}

@Composable
fun PaymentSummaryRow(label: String, value: String, isTotal: Boolean = false) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, fontWeight = if (isTotal) FontWeight.Bold else FontWeight.Normal)
        Text(
            value,
            fontWeight = FontWeight.Bold,
            color = if (isTotal) BluePrimary else Color.Unspecified,
            fontSize = if (isTotal) 18.sp else 14.sp
        )
    }
}
