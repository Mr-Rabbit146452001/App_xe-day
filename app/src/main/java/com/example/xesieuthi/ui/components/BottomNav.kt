package com.example.xesieuthi.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.xesieuthi.SmartCartApp
import com.example.xesieuthi.ui.theme.BluePrimary
import com.example.xesieuthi.ui.theme.BlueSelectedBackground
import com.example.xesieuthi.ui.theme.TextSecondary
import com.example.xesieuthi.ui.theme.XesieuthiTheme

enum class CartTab(
    val title: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    HOME("Trang chủ", Icons.Outlined.Home, Icons.Filled.Home),
    SCAN("Quét mã", Icons.Outlined.QrCodeScanner, Icons.Filled.QrCodeScanner),
    CART("Giỏ hàng", Icons.Outlined.ShoppingCart, Icons.Filled.ShoppingCart),
    SUPPORT("Hỗ trợ", Icons.Outlined.HelpOutline, Icons.Filled.Help),
    PAYMENT("Thanh toán", Icons.Outlined.Payment, Icons.Filled.Payment)
}

@Composable
fun MainBottomNavigation(selectedTab: CartTab, onTabSelected: (CartTab) -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 8.dp
    ) {
        CartTab.entries.forEach { tab ->
            NavigationBarItem(
                selected = selectedTab == tab,
                onClick = { onTabSelected(tab) },
                icon = {
                    Icon(
                        imageVector = if (selectedTab == tab) tab.selectedIcon else tab.icon,
                        contentDescription = tab.title
                    )
                },
                label = { Text(tab.title, fontSize = 10.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = BluePrimary,
                    selectedTextColor = BluePrimary,
                    indicatorColor = BlueSelectedBackground,
                    unselectedIconColor = TextSecondary,
                    unselectedTextColor = TextSecondary
                )
            )
        }
    }
}
