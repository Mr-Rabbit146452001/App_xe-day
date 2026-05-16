package com.example.xesieuthi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ConnectionLostScreen(onRetry: () -> Unit, onCallSupport: () -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var isRetrying by remember { mutableStateOf(false) }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
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
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .size(120.dp)
                                .background(Color(0xFFFFEBEE), CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                Icons.Default.WifiOff,
                                contentDescription = null,
                                tint = Color(0xFFD32F2F),
                                modifier = Modifier.size(64.dp)
                            )
                        }
                        Spacer(Modifier.height(24.dp))
                        Text(
                            "Mất kết nối hệ thống",
                            color = Color(0xFFD32F2F),
                            fontWeight = FontWeight.Bold,
                            fontSize = 22.sp
                        )
                        Spacer(Modifier.height(12.dp))
                        Text(
                            "Không thể đồng bộ giỏ hàng. Vui lòng chờ hoặc gọi nhân viên.",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color.Gray
                        )
                    }
                }
            }

            item {
                ActionCard(
                    title = "Thử lại",
                    description = "Kết nối lại với máy chủ",
                    icon = Icons.Default.Refresh,
                    containerColor = BluePrimary,
                    contentColor = Color.White,
                    onClick = {
                        scope.launch {
                            isRetrying = true
                            snackbarHostState.showSnackbar("Đang thử kết nối lại...")
                            delay(2000)
                            isRetrying = false
                            onRetry()
                        }
                    },
                    isLoading = isRetrying
                )
            }

            item {
                ActionCard(
                    title = "Gọi nhân viên",
                    description = "Yêu cầu hỗ trợ trực tiếp",
                    icon = Icons.Default.HeadsetMic,
                    containerColor = Color.White,
                    contentColor = Color(0xFFD32F2F),
                    borderColor = Color(0xFFD32F2F),
                    onClick = onCallSupport
                )
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF5F5F5)),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Default.Info, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(12.dp))
                        Column {
                            Text("Mã lỗi: ERR_CART_SYNC_014", fontWeight = FontWeight.Bold, fontSize = 12.sp, color = Color.Gray)
                            Text(
                                "Giỏ hàng vật lý vẫn được lưu cục bộ. Đừng lấy sản phẩm ra khỏi xe cho đến khi hệ thống kết nối lại.",
                                fontSize = 12.sp,
                                color = Color.Gray,
                                lineHeight = 16.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ActionCard(
    title: String,
    description: String,
    icon: ImageVector,
    containerColor: Color,
    contentColor: Color,
    borderColor: Color? = null,
    onClick: () -> Unit,
    isLoading: Boolean = false
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = containerColor,
        border = borderColor?.let { androidx.compose.foundation.BorderStroke(1.dp, it) }
    ) {
        Row(
            modifier = Modifier.padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(contentColor.copy(alpha = 0.1f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isLoading) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), color = contentColor, strokeWidth = 2.dp)
                } else {
                    Icon(icon, contentDescription = null, tint = contentColor)
                }
            }
            Spacer(Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(title, fontWeight = FontWeight.Bold, fontSize = 18.sp, color = contentColor)
                Text(description, fontSize = 14.sp, color = contentColor.copy(alpha = 0.8f))
            }
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = contentColor.copy(alpha = 0.5f))
        }
    }
}
