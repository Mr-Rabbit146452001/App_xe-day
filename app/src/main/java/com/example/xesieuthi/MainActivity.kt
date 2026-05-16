package com.example.xesieuthi

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.xesieuthi.data.MockRepository
import com.example.xesieuthi.model.*
import com.example.xesieuthi.ui.components.*
import com.example.xesieuthi.ui.dialogs.DeleteProductConfirmDialog
import com.example.xesieuthi.ui.dialogs.UnscannedItemAlertDialog
import com.example.xesieuthi.ui.screens.*
import com.example.xesieuthi.ui.theme.*
import kotlinx.coroutines.launch

import androidx.compose.ui.tooling.preview.Preview
import com.example.xesieuthi.ui.theme.XesieuthiTheme


enum class ScreenRoute {
    HOME,
    SCAN,
    CART,
    SUPPORT,
    PAYMENT,
    PAYMENT_QR,
    PAYMENT_SUCCESS,
    CONNECTION_LOST,
    SESSION_ENDED
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            XesieuthiTheme {
                SmartCartApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SmartCartApp() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Navigation State
    var currentRoute by rememberSaveable { mutableStateOf(ScreenRoute.HOME) }
    
    // App Data State
    var cartState by remember { mutableStateOf(CartState(items = MockRepository.getMockCartItems())) }
    var sensorStatus by remember { mutableStateOf(MockRepository.getMockSensorStatus()) }
    
    // UI Popups State
    var showUnscannedAlert by rememberSaveable { mutableStateOf(false) }
    var itemToDelete by remember { mutableStateOf<CartItem?>(null) }

    // Bottom Sheet State
    val sheetState = rememberModalBottomSheetState()
    var showProductSheet by remember { mutableStateOf(false) }
    var lastScannedProduct by remember { mutableStateOf<Product?>(null) }

    // Map Route to Bottom Tab
    val activeTab = when (currentRoute) {
        ScreenRoute.HOME -> CartTab.HOME
        ScreenRoute.SCAN -> CartTab.SCAN
        ScreenRoute.CART -> CartTab.CART
        ScreenRoute.SUPPORT -> CartTab.SUPPORT
        ScreenRoute.PAYMENT, ScreenRoute.PAYMENT_QR, ScreenRoute.PAYMENT_SUCCESS -> CartTab.PAYMENT
        else -> CartTab.HOME
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            if (currentRoute != ScreenRoute.SESSION_ENDED && currentRoute != ScreenRoute.CONNECTION_LOST) {
                HeaderSection(
                    customerName = cartState.customer?.name ?: "Khách hàng",
                    battery = sensorStatus.batteryPercent,
                    isWifiConnected = sensorStatus.wifiConnected
                )
            }
        },
        bottomBar = {
            if (currentRoute != ScreenRoute.SESSION_ENDED && currentRoute != ScreenRoute.CONNECTION_LOST && 
                currentRoute != ScreenRoute.PAYMENT_QR && currentRoute != ScreenRoute.PAYMENT_SUCCESS) {
                MainBottomNavigation(
                    selectedTab = activeTab,
                    onTabSelected = { tab ->
                        currentRoute = when (tab) {
                            CartTab.HOME -> ScreenRoute.HOME
                            CartTab.SCAN -> ScreenRoute.SCAN
                            CartTab.CART -> ScreenRoute.CART
                            CartTab.SUPPORT -> ScreenRoute.SUPPORT
                            CartTab.PAYMENT -> ScreenRoute.PAYMENT
                        }
                    }
                )
            }
        },
        containerColor = AppBackground
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (currentRoute) {
                ScreenRoute.HOME -> HomeScreen(
                    onStartShopping = { currentRoute = ScreenRoute.SCAN },
                    onSimulateAlert = { showUnscannedAlert = true }
                )
                ScreenRoute.SCAN -> {
                    if (!cartState.isCustomerLoggedIn) {
                        ScanCustomerScreen(
                            onCustomerScanned = { qr ->
                                scope.launch {
                                    val customer = MockRepository.loginCustomer(qr)
                                    cartState = cartState.copy(isCustomerLoggedIn = true, customer = customer)
                                }
                            },
                            onSkip = { cartState = cartState.copy(isCustomerLoggedIn = true) }
                        )
                    } else {
                        ScanProductScreen(
                            onProductScanned = { barcode ->
                                scope.launch {
                                    val product = MockRepository.getProductByBarcode(barcode)
                                    lastScannedProduct = product
                                    showProductSheet = true
                                }
                            }
                        )
                    }
                }
                ScreenRoute.CART -> CartScreen(
                    cartState = cartState,
                    onUpdateQuantity = { item, delta ->
                        val newItems = cartState.items.toMutableList()
                        val index = newItems.indexOf(item)
                        if (index != -1) {
                            val newQty = item.quantity + delta
                            if (newQty > 0) {
                                newItems[index] = item.copy(quantity = newQty)
                                cartState = cartState.copy(items = newItems)
                            } else {
                                itemToDelete = item
                            }
                        }
                    },
                    onRemoveRequest = { item -> itemToDelete = item },
                    onCheckout = { currentRoute = ScreenRoute.PAYMENT },
                    onContinueShopping = { currentRoute = ScreenRoute.SCAN }
                )
                ScreenRoute.SUPPORT -> SupportRequestScreen()
                ScreenRoute.PAYMENT -> {
                    val subtotal = MockRepository.calculateSubtotal(cartState.items)
                    val discount = MockRepository.calculateDiscount(subtotal)
                    PaymentScreen(
                        items = cartState.items,
                        subtotal = subtotal,
                        discount = discount,
                        onConfirmPayment = { method ->
                            if (method == PaymentMethod.QR_BANKING) {
                                currentRoute = ScreenRoute.PAYMENT_QR
                            } else {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Đã gửi yêu cầu thanh toán qua ${method.name}")
                                }
                            }
                        },
                        onBackToCart = { currentRoute = ScreenRoute.CART }
                    )
                }
                ScreenRoute.PAYMENT_QR -> PaymentQrScreen(
                    onPaymentSuccess = { currentRoute = ScreenRoute.PAYMENT_SUCCESS },
                    onCancel = { currentRoute = ScreenRoute.PAYMENT }
                )
                ScreenRoute.PAYMENT_SUCCESS -> PaymentSuccessScreen(
                    onFinishSession = { currentRoute = ScreenRoute.SESSION_ENDED }
                )
                ScreenRoute.CONNECTION_LOST -> ConnectionLostScreen(
                    onRetry = { currentRoute = ScreenRoute.HOME },
                    onCallSupport = { Toast.makeText(context, "Đã gọi nhân viên hỗ trợ", Toast.LENGTH_SHORT).show() }
                )
                ScreenRoute.SESSION_ENDED -> SessionEndedScreen(
                    onStartNewSession = {
                        cartState = CartState()
                        currentRoute = ScreenRoute.HOME
                    }
                )
            }
        }

        // --- GLOBAL UI ELEMENTS ---

        if (showUnscannedAlert) {
            UnscannedItemAlertDialog(
                onScanNow = {
                    showUnscannedAlert = false
                    currentRoute = ScreenRoute.SCAN
                },
                onRemoved = {
                    showUnscannedAlert = false
                    scope.launch { snackbarHostState.showSnackbar("Đã xác nhận bỏ sản phẩm ra.") }
                },
                onCallSupport = {
                    showUnscannedAlert = false
                    currentRoute = ScreenRoute.SUPPORT
                }
            )
        }

        itemToDelete?.let { item ->
            DeleteProductConfirmDialog(
                cartItem = item,
                onCancel = { itemToDelete = null },
                onConfirm = {
                    cartState = cartState.copy(items = cartState.items.filter { it.product.id != item.product.id })
                    itemToDelete = null
                }
            )
        }

        if (showProductSheet && lastScannedProduct != null) {
            ModalBottomSheet(
                onDismissRequest = { showProductSheet = false },
                sheetState = sheetState,
                containerColor = Color.White
            ) {
                ProductScannedSheet(
                    product = lastScannedProduct!!,
                    onAddToCart = { qty ->
                        val existing = cartState.items.find { it.product.id == lastScannedProduct!!.id }
                        if (existing != null) {
                            val newItems = cartState.items.toMutableList()
                            val index = newItems.indexOf(existing)
                            newItems[index] = existing.copy(quantity = existing.quantity + qty)
                            cartState = cartState.copy(items = newItems)
                        } else {
                            cartState = cartState.copy(items = cartState.items + CartItem(lastScannedProduct!!, qty))
                        }
                        showProductSheet = false
                    },
                    onViewCart = {
                        showProductSheet = false
                        currentRoute = ScreenRoute.CART
                    },
                    onContinue = { showProductSheet = false }
                )
            }
        }
    }

}
