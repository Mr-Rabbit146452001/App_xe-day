package com.example.xesieuthi.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xesieuthi.ui.theme.BluePrimary

@Composable
fun HomeScreen(
    onStartShopping: () -> Unit,
    onSimulateAlert: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Hero Section / Welcome
        Text(
            text = "Chào mừng bạn đến với",
            fontSize = 18.sp,
            color = Color.Gray
        )
        Text(
            text = "Smart Cart",
            fontSize = 32.sp,
            fontWeight = FontWeight.ExtraBold,
            color = BluePrimary
        )
        
        Spacer(modifier = Modifier.height(40.dp))
        
        // Main Action Button
        Button(
            onClick = onStartShopping,
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = null,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "BẮT ĐẦU MUA SẮM",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Quét mã để đăng nhập hoặc quét sản phẩm",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        // Secondary Actions
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            OutlinedButton(
                onClick = onSimulateAlert,
                modifier = Modifier.weight(1f).height(100.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Notifications, contentDescription = null)
                    Text("Test Cảnh báo", textAlign = TextAlign.Center)
                }
            }
            
            OutlinedButton(
                onClick = { /* Tutorial */ },
                modifier = Modifier.weight(1f).height(100.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null)
                    Text("Hướng dẫn", textAlign = TextAlign.Center)
                }
            }
        }
        
        Spacer(modifier = Modifier.height(40.dp))
        
        // Tips or Info
        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
        ) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "💡 Mẹo: Bạn có thể tự quét mã vạch trên sản phẩm để thêm vào giỏ hàng nhanh chóng.",
                    fontSize = 14.sp,
                    color = BluePrimary
                )
            }
        }
    }
}
