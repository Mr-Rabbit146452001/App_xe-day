package com.example.xesieuthi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xesieuthi.data.MockRepository
import com.example.xesieuthi.model.CartItem
import com.example.xesieuthi.model.CartState
import com.example.xesieuthi.ui.theme.*
import com.example.xesieuthi.util.FormatUtils
@Composable
fun CartScreen(
    cartState: CartState,
    onUpdateQuantity: (CartItem, Int) -> Unit,
    onRemoveRequest: (CartItem) -> Unit,
    onCheckout: () -> Unit,
    onContinueShopping: () -> Unit
) {
    val subtotal = MockRepository.calculateSubtotal(cartState.items)
    val discount = MockRepository.calculateDiscount(subtotal)
    val vat = MockRepository.calculateVat(subtotal)
    val total = MockRepository.calculateTotal(subtotal, discount, vat)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Giỏ hàng của bạn",
                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold)
            )
            Surface(
                color = Color(0xFFE5E7EB),
                shape = RoundedCornerShape(20.dp)
            ) {
                Text(
                    text = "${cartState.totalItems} sản phẩm",
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                    fontSize = 12.sp,
                    color = Color.DarkGray
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (cartState.items.isEmpty()) {
            Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.Center) {
                Text("Giỏ hàng của bạn đang trống", color = TextSecondary)
            }
        } else {
            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                items(cartState.items) { item ->
                    CartItemCard(
                        item = item,
                        onIncrease = { onUpdateQuantity(item, 1) },
                        onDecrease = {
                            if (item.quantity > 1) {
                                onUpdateQuantity(item, -1)
                            } else {
                                onRemoveRequest(item)
                            }
                        }
                    )
                }
                
                item {
                    OrderSummaryCard(
                        subtotal = subtotal,
                        discount = discount,
                        vat = vat,
                        total = total,
                        onCheckout = onCheckout,
                        onContinueShopping = onContinueShopping
                    )
                }
            }
        }
    }
}

@Composable
fun CartItemCard(
    item: CartItem,
    onIncrease: () -> Unit,
    onDecrease: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Product Image Placeholder
            Box(
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF3F4F6)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.Image, contentDescription = null, tint = Color.LightGray)
            }

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.product.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    maxLines = 2
                )
                Text(
                    text = "SKU: ${item.product.sku}",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = FormatUtils.formatCurrency(item.product.price),
                    color = BluePrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            }

            // Quantity Controller
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFF3F4F6))
            ) {
                IconButton(onClick = onDecrease, modifier = Modifier.size(36.dp)) {
                    Icon(Icons.Default.Remove, contentDescription = null, modifier = Modifier.size(18.dp))
                }
                Text(
                    text = "${item.quantity}",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(horizontal = 4.dp)
                )
                IconButton(onClick = onIncrease, modifier = Modifier.size(36.dp)) {
                    Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Composable
fun OrderSummaryCard(
    subtotal: Int,
    discount: Int,
    vat: Int,
    total: Int,
    onCheckout: () -> Unit,
    onContinueShopping: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Tổng đơn hàng",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            
            SummaryRow("Tạm tính", FormatUtils.formatCurrency(subtotal))
            SummaryRow("Giảm giá thành viên (10%)", "-${FormatUtils.formatCurrency(discount)}", valueColor = Success)
            SummaryRow("Thuế VAT (8%)", FormatUtils.formatCurrency(vat))
            
            Spacer(modifier = Modifier.height(8.dp))
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), thickness = 1.dp, color = Color(0xFFE5E7EB))
            Spacer(modifier = Modifier.height(8.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("Tổng thanh toán", fontWeight = FontWeight.Medium)
                Text(
                    text = FormatUtils.formatCurrency(total),
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp,
                    color = BluePrimary
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = onCheckout,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
            ) {
                Icon(Icons.Default.ShoppingCartCheckout, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Thanh toán", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            OutlinedButton(
                onClick = onContinueShopping,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0xFFE5E7EB))
            ) {
                Text("Tiếp tục mua sắm", color = Color.Gray, fontWeight = FontWeight.Medium)
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Promo Box
            Surface(
                color = BlueSelectedBackground,
                shape = RoundedCornerShape(12.dp)
            ) {
                Row(
                    modifier = Modifier.padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Info, contentDescription = null, tint = BluePrimary, modifier = Modifier.size(20.dp))
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = "Quét mã thành viên để nhận ưu đãi giảm giá lên tới 20% cho các sản phẩm Organic.",
                        fontSize = 12.sp,
                        color = BluePrimary,
                        lineHeight = 16.sp
                    )
                }
            }
        }
    }
}

@Composable
fun SummaryRow(label: String, value: String, valueColor: Color = Color.Black) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, color = TextSecondary, fontSize = 14.sp)
        Text(text = value, color = valueColor, fontWeight = FontWeight.Medium, fontSize = 14.sp)
    }
}