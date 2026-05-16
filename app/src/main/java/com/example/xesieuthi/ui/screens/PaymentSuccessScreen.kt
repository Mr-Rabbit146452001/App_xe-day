package com.example.xesieuthi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.FileDownload
import androidx.compose.material.icons.filled.QrCode
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
import kotlinx.coroutines.launch

@Composable
fun PaymentSuccessScreen(onFinishSession: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Button(
                onClick = onFinishSession,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
            ) {
                Icon(Icons.Default.Check, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("KẾT THÚC PHIÊN MUA SẮM", fontWeight = FontWeight.Bold)
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
                Column(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(Color(0xFF4CAF50), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(48.dp))
                    }
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Thanh toán thành công!",
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF4CAF50),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        "Cảm ơn bạn đã mua sắm. Giao dịch của bạn đã được xử lý an toàn.",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp),
                        color = Color.Gray
                    )
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text("Hóa đơn điện tử", fontWeight = FontWeight.Bold, fontSize = 20.sp)
                                Text("Mã #HD12345", color = Color.Gray, fontSize = 14.sp)
                            }
                            Icon(
                                Icons.Default.QrCode,
                                contentDescription = null,
                                modifier = Modifier.size(48.dp),
                                tint = Color.DarkGray
                            )
                        }

                        Spacer(Modifier.height(16.dp))
                        ReceiptRow("Thời gian", "14:30 - 24/05/2026")
                        ReceiptRow("Cửa hàng", "Smart Mart Central")
                        ReceiptRow("Thu ngân", "Tự động - Cart #42")

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                        Text("Danh sách sản phẩm", fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 8.dp))
                        SummaryItem("Sữa tươi", "45.000đ")
                        SummaryItem("Táo Envy Mỹ", "120.000đ")
                        SummaryItem("Bánh mì", "15.000đ")

                        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

                        PaymentSummaryRow("Tạm tính", "238.000đ")
                        PaymentSummaryRow("Giảm giá Voucher", "-10.000đ")
                        PaymentSummaryRow("VAT (8%)", "18.240đ")
                        PaymentSummaryRow("Tổng cộng", "246.240đ", isTotal = true)
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = {
                            scope.launch { snackbarHostState.showSnackbar("Đang tạo file hóa đơn...") }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(12.dp)
                    ) {
                        Icon(Icons.Default.FileDownload, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Tải hóa đơn", fontSize = 13.sp)
                    }
                    OutlinedButton(
                        onClick = {
                            scope.launch { snackbarHostState.showSnackbar("Hóa đơn đã được gửi về email khách hàng.") }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(12.dp)
                    ) {
                        Icon(Icons.Default.Email, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Gửi email", fontSize = 13.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun ReceiptRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, color = Color.Gray, fontSize = 14.sp)
        Text(value, fontWeight = FontWeight.Medium, fontSize = 14.sp)
    }
}
