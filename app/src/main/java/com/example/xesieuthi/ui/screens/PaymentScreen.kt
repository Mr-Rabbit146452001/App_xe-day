package com.example.xesieuthi.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xesieuthi.model.CartItem
import com.example.xesieuthi.model.PaymentMethod
import com.example.xesieuthi.ui.theme.*
import com.example.xesieuthi.util.FormatUtils

@Composable
fun PaymentScreen(
    items: List<CartItem>,
    subtotal: Int,
    discount: Int,
    onConfirmPayment: (PaymentMethod) -> Unit,
    onBackToCart: () -> Unit
) {
    var selectedMethod by remember { mutableStateOf(PaymentMethod.QR_BANKING) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Thanh toán",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
        )

        // Card 1: Bill Summary
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ReceiptLong, contentDescription = null, tint = BluePrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Tóm tắt hóa đơn", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }
                
                Spacer(modifier = Modifier.height(16.dp))

                items.forEach { item ->
                    PaymentItemRow(item)
                    Spacer(modifier = Modifier.height(12.dp))
                }

                HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp), color = Color(0xFFF3F4F6))

                SummaryLine("Tổng số lượng", "${items.sumOf { it.quantity }} sản phẩm")
                SummaryLine("Tạm tính", FormatUtils.formatCurrency(subtotal))
                SummaryLine("Giảm giá", "-${FormatUtils.formatCurrency(discount)}", valueColor = Error)

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = BlueSelectedBackground,
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("TỔNG TIỀN:", fontWeight = FontWeight.Bold, color = BluePrimary)
                        Text(
                            text = FormatUtils.formatCurrency(subtotal - discount),
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 22.sp,
                            color = BluePrimary
                        )
                    }
                }
            }
        }

        // Card 2: Payment Methods
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Payments, contentDescription = null, tint = BluePrimary)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Phương thức thanh toán", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Using a Column with Rows to avoid nested scroll issues in verticalScroll
                val methods = listOf(
                    PaymentMethodInfo(PaymentMethod.QR_BANKING, "QR Banking", Icons.Default.QrCodeScanner),
                    PaymentMethodInfo(PaymentMethod.E_WALLET, "Ví điện tử", Icons.Default.AccountBalanceWallet),
                    PaymentMethodInfo(PaymentMethod.MEMBER_CARD, "Thẻ thành viên", Icons.Default.Badge),
                    PaymentMethodInfo(PaymentMethod.CASHIER, "Thanh toán tại quầy", Icons.Default.Store)
                )

                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        PaymentMethodCard(methods[0], selectedMethod == methods[0].method, Modifier.weight(1f)) { selectedMethod = it }
                        PaymentMethodCard(methods[1], selectedMethod == methods[1].method, Modifier.weight(1f)) { selectedMethod = it }
                    }
                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        PaymentMethodCard(methods[2], selectedMethod == methods[2].method, Modifier.weight(1f)) { selectedMethod = it }
                        PaymentMethodCard(methods[3], selectedMethod == methods[3].method, Modifier.weight(1f)) { selectedMethod = it }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { onConfirmPayment(selectedMethod) },
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
                ) {
                    Icon(Icons.Default.CheckCircle, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Xác nhận thanh toán", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = onBackToCart,
                    modifier = Modifier.fillMaxWidth().height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    border = BorderStroke(1.dp, Color(0xFFE5E7EB))
                ) {
                    Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Quay lại giỏ hàng", color = Color.Gray, fontWeight = FontWeight.Medium)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun PaymentItemRow(item: CartItem) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier.size(50.dp).clip(RoundedCornerShape(8.dp)).background(Color(0xFFF3F4F6)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Default.Image, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(24.dp))
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(item.product.name, fontSize = 14.sp, fontWeight = FontWeight.Medium, maxLines = 1)
            Text("Số lượng: ${item.quantity}", fontSize = 12.sp, color = TextSecondary)
        }
        Text(FormatUtils.formatCurrency(item.product.price * item.quantity), fontWeight = FontWeight.Bold, fontSize = 15.sp)
    }
}

@Composable
fun SummaryLine(label: String, value: String, valueColor: Color = Color.Black) {
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
        Text(label, color = TextSecondary, fontSize = 14.sp)
        Text(value, color = valueColor, fontWeight = FontWeight.Medium, fontSize = 14.sp)
    }
}

data class PaymentMethodInfo(val method: PaymentMethod, val title: String, val icon: ImageVector)

@Composable
fun PaymentMethodCard(
    info: PaymentMethodInfo,
    isSelected: Boolean,
    modifier: Modifier = Modifier,
    onSelect: (PaymentMethod) -> Unit
) {
    Surface(
        modifier = modifier
            .height(100.dp)
            .clickable { onSelect(info.method) },
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) BlueSelectedBackground else Color.White,
        border = BorderStroke(2.dp, if (isSelected) BluePrimary else Color(0xFFF3F4F6))
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = info.icon,
                contentDescription = null,
                tint = if (isSelected) BluePrimary else TextSecondary,
                modifier = Modifier.size(32.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = info.title,
                fontSize = 12.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) BluePrimary else TextPrimary,
                textAlign = TextAlign.Center,
                lineHeight = 14.sp
            )
        }
    }
}
