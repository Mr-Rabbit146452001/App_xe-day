package com.example.xesieuthi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xesieuthi.ui.theme.BluePrimary
import kotlinx.coroutines.launch

data class SupportReason(
    val title: String,
    val icon: ImageVector
)

@Composable
fun SupportRequestScreen() {
    val reasons = listOf(
        SupportReason("Không quét được mã", Icons.Default.QrCodeScanner),
        SupportReason("Sản phẩm không có giá", Icons.Default.LocalOffer),
        SupportReason("Muốn hủy giao dịch", Icons.Default.Close),
        SupportReason("Xe đẩy gặp lỗi", Icons.Default.ShoppingCart)
    )

    var selectedReason by remember { mutableStateOf<SupportReason?>(null) }
    var showDialog by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(onClick = { showDialog = false }) {
                    Text("Đóng")
                }
            },
            title = { Text("Đã gửi yêu cầu hỗ trợ") },
            text = { Text("Nhân viên sẽ đến xe của bạn trong ít phút.") },
            icon = { Icon(Icons.Default.CheckCircle, contentDescription = null, tint = Color(0xFF4CAF50)) }
        )
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        bottomBar = {
            Button(
                onClick = {
                    if (selectedReason == null) {
                        scope.launch { snackbarHostState.showSnackbar("Vui lòng chọn lý do cần hỗ trợ.") }
                    } else {
                        showDialog = true
                        // TODO: gửi support request lên backend
                        // TODO: gửi vị trí xe/cartId cho nhân viên
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC62828))
            ) {
                Icon(Icons.Default.HeadsetMic, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("GỌI NHÂN VIÊN", fontWeight = FontWeight.Bold)
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            item {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(BluePrimary.copy(alpha = 0.1f), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(Icons.Default.HelpOutline, contentDescription = null, tint = BluePrimary, modifier = Modifier.size(32.dp))
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("Cần hỗ trợ?", fontSize = 24.sp, fontWeight = FontWeight.Bold)
                    Text(
                        "Vui lòng chọn lý do để chúng tôi hỗ trợ bạn tốt nhất:",
                        textAlign = TextAlign.Center,
                        color = Color.Gray,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            item {
                // We use a Column of Rows instead of LazyVerticalGrid inside LazyColumn
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    reasons.chunked(2).forEach { pair ->
                        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            pair.forEach { reason ->
                                SupportReasonCard(
                                    reason = reason,
                                    isSelected = selectedReason == reason,
                                    onClick = { selectedReason = reason },
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Giỏ hàng của bạn", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Spacer(Modifier.height(12.dp))
                        SummaryItem("Táo Envy Mỹ 1kg", "45.000đ")
                        SummaryItem("Sữa tươi TH True Milk 1L", "35.000đ")
                        Text(
                            "Và 3 sản phẩm khác...",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Tổng cộng:", fontWeight = FontWeight.Bold)
                            Text("345.000đ", fontWeight = FontWeight.Bold, color = BluePrimary, fontSize = 18.sp)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SupportReasonCard(
    reason: SupportReason,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .clickable { onClick() }
            .border(
                width = 2.dp,
                color = if (isSelected) BluePrimary else Color.Transparent,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isSelected) BluePrimary.copy(alpha = 0.05f) else Color.White
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(Color(0xFFF5F5F5), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(reason.icon, contentDescription = null, tint = if (isSelected) BluePrimary else Color.Gray)
            }
            Spacer(Modifier.height(12.dp))
            Text(
                reason.title,
                textAlign = TextAlign.Center,
                fontSize = 13.sp,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                color = if (isSelected) BluePrimary else Color.DarkGray,
                lineHeight = 16.sp
            )
        }
    }
}
