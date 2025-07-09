@file:OptIn(ExperimentalPermissionsApi::class)

package com.behnamuix.colordetect

import android.app.Activity
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.behnamuix.colordetect.ui.theme.PurpleGrey80
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

import java.util.concurrent.Executors


@Composable
fun ColorDetectionApp() {


    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    LaunchedEffect(cameraPermissionState.status) {
        if (!cameraPermissionState.status.isGranted &&
            !cameraPermissionState.status.shouldShowRationale
        ) {
            cameraPermissionState.launchPermissionRequest()
        }
    }

    when {
        cameraPermissionState.status.isGranted -> CameraPreviewWithColorDetection()
        cameraPermissionState.status.shouldShowRationale -> PermissionRationaleScreen {
            cameraPermissionState.launchPermissionRequest()
        }

        else -> PermissionRequestScreen {
            cameraPermissionState.launchPermissionRequest()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraPreviewWithColorDetection() {
    val context = LocalContext.current
    val lifecycleOwner = androidx.lifecycle.compose.LocalLifecycleOwner.current

    var detectedColor by remember { mutableStateOf(Color.White) }
    var rgbValues by remember { mutableStateOf("RGB: -, -, -") }
    var hexValue by remember { mutableStateOf("------") }

    val cameraProviderFuture = remember { ProcessCameraProvider.getInstance(context) }
    val executor = remember { Executors.newSingleThreadExecutor() }

    Box(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            factory = { ctx ->
                PreviewView(ctx).apply {
                    implementationMode = PreviewView.ImplementationMode.PERFORMANCE
                    scaleType = PreviewView.ScaleType.FILL_CENTER

                    val cameraProvider = cameraProviderFuture.get()
                    val preview = Preview.Builder().build()
                    val imageAnalysis = ImageAnalysis.Builder()
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
                        .build()
                        .also {
                            it.setAnalyzer(executor, ColorAnalyzer { color, rgb, hex ->
                                detectedColor = color
                                rgbValues = rgb
                                hexValue = hex
                            })
                        }

                    try {
                        cameraProvider.unbindAll()
                        cameraProvider.bindToLifecycle(
                            lifecycleOwner,
                            CameraSelector.DEFAULT_BACK_CAMERA,
                            preview,
                            imageAnalysis
                        )
                        preview.setSurfaceProvider(surfaceProvider)
                    } catch (e: Exception) {
                        Log.e("Camera", "خطا در راه‌اندازی دوربین", e)
                    }
                }
            },
            modifier = Modifier.fillMaxSize()
        )

        ColorInfoBox(detectedColor, rgbValues, hexValue)
    }
}

@Composable
fun ColorInfoBox(color: Color, rgb: String, hex: String) {

    var hexKeep by remember { mutableStateOf("#FFFFFF") }

    var colorNameResult by remember { mutableStateOf("") }
    val colorMapper = remember { ColorMapper() }
    colorNameResult = colorMapper.getColorName(hex)


    //Circle_Center
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .border(1.dp, Color(0xFFFFEB3B), CircleShape)
        )
        Box(
            modifier = Modifier
                .size(15.dp)
                .border(0.5.dp, Color(0xFFF44336), CircleShape)
        )

    }
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 60.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Card(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp)
                        .fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFFFFF))

                ) {

                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row() {
                            CopyableText(hexKeep)
                            Card(
                                colors = CardDefaults.cardColors(containerColor = Color(0xff070707)),
                                modifier = Modifier
                                    .padding(12.dp),
                                elevation = CardDefaults.elevatedCardElevation(8.dp)
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
                                        .padding(16.dp),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Box(

                                        modifier = Modifier
                                            .padding(8.dp)
                                            .size(80.dp)
                                            .background(color, CircleShape)
                                            .border(2.dp, Color.White, CircleShape)
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "HEX:$hex",
                                        fontWeight = FontWeight.Bold,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = Color.White
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))


                                    Text(
                                        text = rgb,
                                        style = MaterialTheme.typography.titleSmall,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )


                                }
                            }
                        }
                    }
                    Box() {

                        Spacer(modifier = Modifier.height(12.dp))

                        Button(
                            modifier = Modifier
                                .fillMaxWidth(),


                            colors = ButtonDefaults.buttonColors(containerColor = PurpleGrey80),
                            shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp),
                            onClick = { hexKeep = hex }
                        ) {
                            Text(
                                modifier = Modifier
                                    .padding(12.dp),
                                text = "تشخیص نهایی",
                                color = Color.White,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }


                }


            }


        }


    }


}



@Composable
fun CopyableText(textToCopy: String) {
    val context = LocalContext.current
    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    Text(
        text = textToCopy,
        modifier = Modifier
            .clickable {
                // ایجاد یک ClipData برای قرار دادن متن در کلیپ‌بورد
                val clip = ClipData.newPlainText("label", textToCopy)
                clipboardManager.setPrimaryClip(clip)
                //WebViewColor(textToCopy)


                // نمایش یک پیام کوچک (Toast) به کاربر
            }
            .padding(8.dp)
            .fillMaxWidth(0.4f),
        color = PurpleGrey80,
        fontWeight = FontWeight.Bold,
        style = MaterialTheme.typography.titleMedium

    )
}

@Composable
fun WebViewColor(url: String) {
    TODO("d")
}

@Composable
fun PermissionRequestScreen(onRequestPermission: () -> Unit) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_camera),
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                tint = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "برای استفاده از تشخیص رنگ، لطفاً دسترسی دوربین را فعال کنید",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = onRequestPermission,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("درخواست دسترسی به دوربین")
            }
        }



}

@Composable
fun PermissionRationaleScreen(onRequestPermission: () -> Unit) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Icon(
            painter = painterResource(R.drawable.icon_camera),
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "برای تشخیص رنگ، برنامه نیاز به دسترسی به دوربین دارد",
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "این برنامه از دوربین فقط برای تشخیص رنگ استفاده می‌کند و از تصاویر شما ذخیره‌ای نگه نمی‌دارد",
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onRequestPermission,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("فعال کردن دسترسی")
        }
    }
}

class ColorAnalyzer(
    private val onColorDetected: (Color, String, String) -> Unit
) : ImageAnalysis.Analyzer {
    @ExperimentalGetImage
    override fun analyze(imageProxy: ImageProxy) {
        try {
            val image = imageProxy.image ?: return
            val buffer = image.planes[0].buffer
            val pixelStride = image.planes[0].pixelStride
            val rowStride = image.planes[0].rowStride

            val centerX = image.width / 2
            val centerY = image.height / 2
            val offset = centerY * rowStride + centerX * pixelStride

            val r = buffer[offset].toInt() and 0xFF
            val g = buffer[offset + 1].toInt() and 0xFF
            val b = buffer[offset + 2].toInt() and 0xFF

            val color = Color(r, g, b)
            val hex = "#%02X%02X%02X".format(r, g, b)

            onColorDetected(color, "RGB: $r, $g, $b", "$hex")
        } catch (e: Exception) {
            Log.e("ColorAnalyzer", "خطا در تحلیل تصویر", e)
        } finally {
            imageProxy.close()
        }
    }
}

@Composable
fun ColorDetectionAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF6750A4),
            secondary = Color(0xFFEADDFF),
            tertiary = Color(0xFF7D5260)
        ),
        content = content
    )
}



