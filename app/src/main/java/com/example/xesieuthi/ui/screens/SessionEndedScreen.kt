package com.example.xesieuthi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xesieuthi.ui.theme.BluePrimary

@Composable
fun SessionEndedScreen(onStartNewSession: () -> Unit) {
    Scaffold(
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                InfoItem(Icons.Default.Shield, "Kết nối an toàn")
                InfoItem(Icons.Default.Engineering, "Hỗ trợ kỹ thuật")
                InfoItem(Icons.Default.Language, "Tiếng Việt")
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
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                Brush.verticalGradient(
                                    colors = listOf(BluePrimary.copy(alpha = 0.05f), Color.White)
                                )
                            )
                    ) {
                        Column(
                            modifier = Modifier.padding(24.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(80.dp)
                                    .background(BluePrimary, CircleShape),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Check, contentDescription = null, tint = Color.White, modifier = Modifier.size(40.dp))
                            }
                            Spacer(Modifier.height(24.dp))
                            Text(
                                "Phiên mua sắm đã kết thúc",
                                fontSize = 26.sp,
                                fontWeight = FontWeight.Bold,
                                color = BluePrimary,
                                textAlign = TextAlign.Center,
                                lineHeight = 32.sp
                            )
                            Spacer(Modifier.height(16.dp))
                            Text(
                                "Vui lòng đưa xe về khu vực quy định. Cảm ơn quý khách đã tin tưởng sử dụng dịch vụ của chúng tôi!",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyLarge,
                                color = Color.Gray,
                                lineHeight = 24.sp
                            )
                            Spacer(Modifier.height(16.dp))
                            // Placeholder for visual element
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(120.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .background(Color(0xFFF0F4F8)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("SMART MART", fontWeight = FontWeight.Black, color = BluePrimary.copy(alpha = 0.2f), fontSize = 32.sp)
                            }
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .background(Color(0xFFF5F5F5), RoundedCornerShape(12.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(Icons.Default.CleaningServices, contentDescription = null, tint = Color.Gray)
                        }
                        Spacer(Modifier.width(16.dp))
                        Column {
                            Text("Bảo mật thông tin", fontWeight = FontWeight.Bold)
                            Text("Dữ liệu cá nhân của bạn đã được xóa an toàn.", fontSize = 13.sp, color = Color.Gray)
                        }
                    }
                }
            }

            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = BluePrimary),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Sẵn sàng cho lượt tiếp theo?", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text(
                            "Hệ thống đã sẵn sàng để phục vụ khách hàng mới.",
                            color = Color.White.copy(alpha = 0.8f),
                            fontSize = 14.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                        Spacer(Modifier.height(8.dp))
                        Button(
                            onClick = onStartNewSession,
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = BluePrimary),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Icon(Icons.Default.Refresh, contentDescription = null)
                            Spacer(Modifier.width(8.dp))
                            Text("Bắt đầu phiên mới", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun InfoItem(icon: ImageVector, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
        Spacer(Modifier.height(4.dp))
        Text(text, fontSize = 10.sp, color = Color.Gray)
    }
}