package com.example.xesieuthi.ui.preview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.xesieuthi.ui.theme.AppBackground
import com.example.xesieuthi.ui.theme.XesieuthiTheme

/** Tablet ngang — gần với màn hình xe đẩy thông minh. */
const val SMART_CART_DEVICE =
    "spec:width=1280dp,height=800dp,dpi=240,orientation=landscape"

/** Điện thoại dọc — xem nhanh từng màn hình. */
const val PHONE_DEVICE =
    "spec:width=411dp,height=891dp,dpi=420,orientation=portrait"

/**
 * Bọc nội dung preview trong theme và nền giống app thật.
 * Dùng cho mọi @Preview trong [ScreenPreviews].
 */
@Composable
fun AppPreviewTheme(content: @Composable () -> Unit) {
    XesieuthiTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppBackground
        ) {
            content()
        }
    }
}

@Preview(name = "Theme", showBackground = true, device = PHONE_DEVICE)
@Composable
private fun AppPreviewThemePreview() {
    AppPreviewTheme {
        Surface(color = AppBackground) {}
    }
}
