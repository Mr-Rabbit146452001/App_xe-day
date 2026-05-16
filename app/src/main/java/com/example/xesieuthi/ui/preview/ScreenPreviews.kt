package com.example.xesieuthi.ui.preview

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.xesieuthi.data.MockRepository
import com.example.xesieuthi.model.CartState
import com.example.xesieuthi.model.Customer
import com.example.xesieuthi.ui.components.CartTab
import com.example.xesieuthi.ui.components.HeaderSection
import com.example.xesieuthi.ui.components.MainBottomNavigation
import com.example.xesieuthi.ui.screens.CartScreen
import com.example.xesieuthi.ui.screens.ConnectionLostScreen
import com.example.xesieuthi.ui.screens.HomeScreen
import com.example.xesieuthi.ui.screens.PaymentQrScreen
import com.example.xesieuthi.ui.screens.PaymentScreen
import com.example.xesieuthi.ui.screens.PaymentSuccessScreen
import com.example.xesieuthi.ui.screens.ProductScannedSheet
import com.example.xesieuthi.ui.screens.ScanCustomerScreen
import com.example.xesieuthi.ui.screens.ScanProductScreen
import com.example.xesieuthi.ui.screens.SessionEndedScreen
import com.example.xesieuthi.ui.screens.SupportRequestScreen
import com.example.xesieuthi.ui.theme.AppBackground

private val noop: () -> Unit = {}
private val noopString: (String) -> Unit = {}
private val noopItemQty: (com.example.xesieuthi.model.CartItem, Int) -> Unit = { _, _ -> }
private val noopItem: (com.example.xesieuthi.model.CartItem) -> Unit = {}

// region — Trang chủ & điều hướng

@Preview(
    name = "01 · Trang chủ",
    group = "Màn hình chính",
    showBackground = true,
    showSystemUi = true,
    device = SMART_CART_DEVICE
)
@Composable
fun PreviewHomeScreen() {
    AppPreviewTheme {
        HomeScreen(onStartShopping = noop, onSimulateAlert = noop)
    }
}

@Preview(
    name = "01b · Trang chủ (có header & tab)",
    group = "Màn hình chính",
    showBackground = true,
    showSystemUi = true,
    device = SMART_CART_DEVICE
)
@Composable
fun PreviewHomeScreenWithChrome() {
    AppPreviewTheme {
        Scaffold(
            containerColor = AppBackground,
            topBar = {
                HeaderSection(
                    customerName = "Nguyễn Văn A",
                    battery = 85,
                    isWifiConnected = true
                )
            },
            bottomBar = {
                MainBottomNavigation(selectedTab = CartTab.HOME, onTabSelected = {})
            }
        ) { padding ->
            Box(Modifier.padding(padding).fillMaxSize()) {
                HomeScreen(onStartShopping = noop, onSimulateAlert = noop)
            }
        }
    }
}

@Preview(
    name = "12 · Thanh điều hướng",
    group = "Thành phần",
    showBackground = true,
    device = PHONE_DEVICE
)
@Composable
fun PreviewBottomNavigation() {
    AppPreviewTheme {
        MainBottomNavigation(selectedTab = CartTab.CART, onTabSelected = {})
    }
}

// endregion

// region — Quét mã

@Preview(
    name = "02 · Quét mã khách hàng",
    group = "Quét mã",
    showBackground = true,
    showSystemUi = true,
    device = SMART_CART_DEVICE
)
@Composable
fun PreviewScanCustomerScreen() {
    AppPreviewTheme {
        ScanCustomerScreen(onCustomerScanned = noopString, onSkip = noop)
    }
}

@Preview(
    name = "03 · Quét mã sản phẩm",
    group = "Quét mã",
    showBackground = true,
    showSystemUi = true,
    device = SMART_CART_DEVICE
)
@Composable
fun PreviewScanProductScreen() {
    AppPreviewTheme {
        ScanProductScreen(onProductScanned = noopString)
    }
}

@Preview(
    name = "04 · Sheet sản phẩm vừa quét",
    group = "Quét mã",
    showBackground = true,
    heightDp = 520,
    device = PHONE_DEVICE
)
@Composable
fun PreviewProductScannedSheet() {
    AppPreviewTheme {
        Surface(color = Color.White) {
            ProductScannedSheet(
                product = MockRepository.sampleProducts.first(),
                onAddToCart = {},
                onViewCart = noop,
                onContinue = noop
            )
        }
    }
}

// endregion

// region — Giỏ hàng

@Preview(
    name = "05 · Giỏ hàng (có sản phẩm)",
    group = "Giỏ hàng",
    showBackground = true,
    showSystemUi = true,
    device = SMART_CART_DEVICE
)
@Composable
fun PreviewCartScreenWithItems() {
    AppPreviewTheme {
        CartScreen(
            cartState = CartState(
                items = MockRepository.getMockCartItems(),
                isCustomerLoggedIn = true,
                customer = Customer("CUST001", "Nguyễn Văn A", "MEM999888")
            ),
            onUpdateQuantity = noopItemQty,
            onRemoveRequest = noopItem,
            onCheckout = noop,
            onContinueShopping = noop
        )
    }
}

@Preview(
    name = "05b · Giỏ hàng (trống)",
    group = "Giỏ hàng",
    showBackground = true,
    showSystemUi = true,
    device = SMART_CART_DEVICE
)
@Composable
fun PreviewCartScreenEmpty() {
    AppPreviewTheme {
        CartScreen(
            cartState = CartState(),
            onUpdateQuantity = noopItemQty,
            onRemoveRequest = noopItem,
            onCheckout = noop,
            onContinueShopping = noop
        )
    }
}

// endregion

// region — Thanh toán

@Preview(
    name = "06 · Chọn phương thức thanh toán",
    group = "Thanh toán",
    showBackground = true,
    showSystemUi = true,
    device = SMART_CART_DEVICE
)
@Composable
fun PreviewPaymentScreen() {
    val items = MockRepository.getMockPaymentItems()
    val subtotal = MockRepository.calculateSubtotal(items)
    val discount = MockRepository.calculateDiscount(subtotal)

    AppPreviewTheme {
        PaymentScreen(
            items = items,
            subtotal = subtotal,
            discount = discount,
            onConfirmPayment = {},
            onBackToCart = noop
        )
    }
}

@Preview(
    name = "07 · Thanh toán QR",
    group = "Thanh toán",
    showBackground = true,
    showSystemUi = true,
    device = SMART_CART_DEVICE
)
@Composable
fun PreviewPaymentQrScreen() {
    AppPreviewTheme {
        PaymentQrScreen(onPaymentSuccess = noop, onCancel = noop)
    }
}

@Preview(
    name = "08 · Thanh toán thành công",
    group = "Thanh toán",
    showBackground = true,
    showSystemUi = true,
    device = SMART_CART_DEVICE
)
@Composable
fun PreviewPaymentSuccessScreen() {
    AppPreviewTheme {
        PaymentSuccessScreen(onFinishSession = noop)
    }
}

// endregion

// region — Hỗ trợ & lỗi

@Preview(
    name = "09 · Yêu cầu hỗ trợ",
    group = "Hỗ trợ",
    showBackground = true,
    showSystemUi = true,
    device = SMART_CART_DEVICE
)
@Composable
fun PreviewSupportRequestScreen() {
    AppPreviewTheme {
        SupportRequestScreen()
    }
}

@Preview(
    name = "10 · Mất kết nối",
    group = "Hỗ trợ",
    showBackground = true,
    showSystemUi = true,
    device = SMART_CART_DEVICE
)
@Composable
fun PreviewConnectionLostScreen() {
    AppPreviewTheme {
        ConnectionLostScreen(onRetry = noop, onCallSupport = noop)
    }
}

@Preview(
    name = "11 · Kết thúc phiên",
    group = "Hỗ trợ",
    showBackground = true,
    showSystemUi = true,
    device = SMART_CART_DEVICE
)
@Composable
fun PreviewSessionEndedScreen() {
    AppPreviewTheme {
        SessionEndedScreen(onStartNewSession = noop)
    }
}

// endregion
