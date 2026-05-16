package com.example.xesieuthi.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FlashlightOn
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.xesieuthi.model.Product
import com.example.xesieuthi.ui.theme.BluePrimary
import com.example.xesieuthi.ui.theme.TextSecondary

@Composable
fun ScanCustomerScreen(
    onCustomerScanned: (String) -> Unit,
    onSkip: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.QrCodeScanner,
            contentDescription = null,
            modifier = Modifier.size(100.dp),
            tint = BluePrimary
        )
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Text(
            text = "Quét mã khách hàng",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Vui lòng đưa mã QR thành viên của bạn vào vùng nhận diện để đăng nhập và nhận ưu đãi.",
            textAlign = TextAlign.Center,
            color = TextSecondary
        )
        
        Spacer(modifier = Modifier.height(48.dp))
        
        // Mock Scanning Area
        Box(
            modifier = Modifier
                .size(250.dp)
                .background(Color.LightGray.copy(alpha = 0.2f), RoundedCornerShape(24.dp))
                .padding(2.dp),
            contentAlignment = Alignment.Center
        ) {
            // This would be the Camera Preview in a real app
            Text("Camera Preview", color = Color.Gray)
            
            // Simulation Button
            Button(
                onClick = { onCustomerScanned("CUST-12345") },
                modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
            ) {
                Text("Giả lập quét mã")
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        TextButton(onClick = onSkip) {
            Text("Bỏ qua đăng nhập", color = BluePrimary)
        }
    }
}

@Composable
fun ScanProductScreen(
    onProductScanned: (String) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        // Camera Preview Placeholder
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Đang mở camera...", color = Color.White)
            Spacer(modifier = Modifier.height(20.dp))
            // Simulated scan trigger
            Button(onClick = { onProductScanned("8934567890123") }) {
                Text("Quét thử sản phẩm")
            }
        }

        // Overlay UI
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Quét mã vạch sản phẩm",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 40.dp)
            )
            
            Spacer(modifier = Modifier.weight(1f))
            
            // Scanning Frame
            Box(
                modifier = Modifier
                    .size(width = 300.dp, height = 200.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                // Drawing corners or something here would be nice
            }
            
            Spacer(modifier = Modifier.weight(1f))
            
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 40.dp),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                FloatingActionButton(
                    onClick = { /* Flash */ },
                    containerColor = Color.White.copy(alpha = 0.2f),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.FlashlightOn, contentDescription = "Flash")
                }
                
                FloatingActionButton(
                    onClick = { /* Input code manually */ },
                    containerColor = Color.White.copy(alpha = 0.2f),
                    contentColor = Color.White
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Manual")
                }
            }
        }
    }
}

@Composable
fun ProductScannedSheet(
    product: Product,
    onAddToCart: (Int) -> Unit,
    onViewCart: () -> Unit,
    onContinue: () -> Unit
) {
    var quantity by remember { mutableStateOf(1) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        Text(
            text = "Đã tìm thấy sản phẩm",
            style = MaterialTheme.typography.labelLarge,
            color = BluePrimary
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Product Image Placeholder
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .background(Color(0xFFF3F4F6), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.ShoppingCart, contentDescription = null, tint = Color.LightGray, modifier = Modifier.size(48.dp))
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            Column {
                Text(
                    text = product.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${product.price}đ",
                    style = MaterialTheme.typography.headlineSmall,
                    color = BluePrimary,
                    fontWeight = FontWeight.ExtraBold
                )
                Text(
                    text = "Đơn vị: ${product.sku}", // Fallback to sku or name if unit is missing
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextSecondary
                )
            }
        }
        
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(text = "Số lượng", fontWeight = FontWeight.Bold)
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            FilledIconButton(
                onClick = { if (quantity > 1) quantity-- },
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFFF3F4F6))
            ) {
                Text("-", fontSize = 24.sp, color = Color.Black)
            }
            
            Text(
                text = "$quantity",
                modifier = Modifier.padding(horizontal = 32.dp),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            
            FilledIconButton(
                onClick = { quantity++ },
                colors = IconButtonDefaults.filledIconButtonColors(containerColor = Color(0xFFF3F4F6))
            ) {
                Text("+", fontSize = 24.sp, color = Color.Black)
            }
        }
        
        Spacer(modifier = Modifier.height(32.dp))
        
        Button(
            onClick = { onAddToCart(quantity) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = BluePrimary)
        ) {
            Text("Thêm vào giỏ hàng", fontWeight = FontWeight.Bold, fontSize = 16.sp)
        }
        
        Spacer(modifier = Modifier.height(12.dp))
        
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(
                onClick = onContinue,
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Tiếp tục quét")
            }
            OutlinedButton(
                onClick = onViewCart,
                modifier = Modifier.weight(1f).height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Xem giỏ hàng")
            }
        }
    }
}
