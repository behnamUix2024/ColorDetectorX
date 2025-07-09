package com.behnamuix.colordetect

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.behnamuix.colordetect.myket.util.IabHelper

import com.behnamuix.colordetect.ui.theme.ColorDetectTheme
import com.behnamuix.colordetect.ui.theme.PurpleGrey80
import com.behnamuix.tenserpingx.AndroidWraper.DeviceInfo
import ir.myket.billingclient.util.Purchase
import ir.myket.billingclient.util.Security
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import androidx.core.content.edit

class MyketActivity : ComponentActivity() {
    val SKU_PREMIUM: String = "color_detect"
    var buy = mutableStateOf(false)
    val RC_REQUEST: Int = 10002
    private val KEY_PREMIUM_PURCHASED = "first_launch"
    lateinit var mHelper: IabHelper
    private val sharedPreferences by lazy {
        getSharedPreferences("my", MODE_PRIVATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        mHelper = IabHelper(this, BuildConfig.IAB_PUBLIC_KEY)
        mHelper.enableDebugLogging(true)
        setContent {
            ColorDetectTheme {
                var first = sharedPreferences.getBoolean(KEY_PREMIUM_PURCHASED, false)
                if (first) {

                    goToMain()
                } else {
                    BuyScreen()
                }
            }
        }
    }

    fun goToMain() {
        startActivity(
            Intent(
                this,
                MainActivity()::class.java
            )
        )
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun BuyScreen() {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF1A237E),  // Navy
                            Color(0xFF283593),  // Darker
                            Color(0xFF03A9F4)   // Me
                        )
                    )
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)

            ) {
                Image(
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .width(200.dp)
                        .height(200.dp),
                    painter = painterResource(R.drawable.color_picker),
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text="رنگ یاب",
                    color=Color.White,
                    style = MaterialTheme.typography.displayLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text="تشخیص رنگ با الگوریتم هوشمند و دقیق!",
                    color=Color(0xFFCECECE),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text="version 1.0.0",
                    color=Color.White,
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(32.dp))

            }


            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .fillMaxSize().padding(bottom = 60.dp)


            ) {
                Card(
                    modifier = Modifier
                        .padding(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.elevatedCardElevation(8.dp),

                    shape = RoundedCornerShape(12.dp)
                ) {

                    Column(

                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(12.dp),
                    ) {

                        Text(
                            text = " با تشکر از نصب اپلیکیشن.همین حالا با 20% تخفیف این نسخه را خریداری کنید!",
                            style = MaterialTheme.typography.bodyLarge,

                            )
                        Spacer(modifier = Modifier.height(24.dp))
                        Button(
                            colors = ButtonDefaults.buttonColors(containerColor = PurpleGrey80),
                            shape = RoundedCornerShape(12.dp),
                            onClick = {
                                payConfig()

                            },
                            modifier = Modifier.align(Alignment.End).fillMaxWidth()
                        ) {
                            Row (){

                                Text(
                                    modifier = Modifier
                                        .padding(4.dp),
                                    style = MaterialTheme.typography.bodyLarge,
                                    text = "خریداری نسخه پرمیوم"
                                )
                                Icon(
                                    painter = painterResource(R.drawable.bag),
                                    contentDescription = ""
                                )
                            }

                        }
                        Spacer(modifier = Modifier.height(8.dp))

                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth(),
                            color = PurpleGrey80
                        )
                    }
                }
            }

        }
    }

    private fun payConfig() {
        Log.d("TAG", "Initializing IAB with key: ${BuildConfig.IAB_PUBLIC_KEY}")
        Log.d("TAG", "Checking SKU: $SKU_PREMIUM")

        mHelper.startSetup { result ->
            Log.d("TAG", "Setup result: ${result.isSuccess}, ${result.message}")
            if (result.isSuccess) {
                Toast.makeText(
                    this,
                    "در حال ارتباط با سرور های مایکت هستیم اندکی صبر کنید",
                    Toast.LENGTH_SHORT
                ).show()
                payIntent()
            } else {
                Toast.makeText(this, "خطا در راه‌اندازی: ${result.message}", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    private fun payIntent() {
        // 1. بررسی وجود mHelper (آبجکت مدیریت خرید درون‌برنامه‌ای)

        // 2. تعریف لیسنر برای رویداد مصرف (consume) محصول
        IabHelper.OnConsumeFinishedListener { purchase, result ->
            when {
                // 2-1. اگر mHelper نال بود عملیات متوقف می‌شود
                false -> return@OnConsumeFinishedListener

                // 2-2. اگر مصرف موفقیت‌آمیز بود
                result?.isSuccess == true -> Log.d("TAG", "Consumption successful. Provisioning.")

                // 2-3. در صورت خطا در مصرف محصول
                else -> Log.e("TAG", "Error while consuming: $result")
            }
        }

        // 3. تعریف لیسنر برای بررسی موجودی محصولات
        val inventoryListener = IabHelper.QueryInventoryFinishedListener { result, inv ->
            when {
                // 3-1. اگر خطا در دریافت لیست محصولات
                result?.isFailure == true -> {
                    Log.e("TAG", "Failed to query inventory: $result")
                    return@QueryInventoryFinishedListener
                }

                // 3-2. اگر محصول مورد نظر قبلا خریداری شده
                inv?.getPurchase(SKU_PREMIUM) != null -> {
                    inv.getPurchase(SKU_PREMIUM)?.let { purchase ->
                        if (developerPayload(purchase) == true) {
                            // محصول قبلاً خریداری شده و payload معتبر است.
                            // برای محصولات مصرف نشدنی، نیازی به مصرف نیست.
                            Log.d("TAG", "User already owns the non-consumable item: $SKU_PREMIUM")
                            // در اینجا می‌توانید وضعیت پریمیوم کاربر را فعال کنید
                            setFirstLaunchStatus(true) // فرض بر اینکه 'perm' وضعیت پریمیوم را نگه می‌دارد
                            goToMain()
                            // اگر کاربر قبلاً خریده، ممکن است بخواهید مستقیماً محتوای پریمیوم را نمایش دهید
                            return@QueryInventoryFinishedListener
                        } else {
                            // payload نامعتبر است
                            Log.e("TAG", "Error: Invalid payload for owned item")
                            return@QueryInventoryFinishedListener
                        }
                    }
                }

                // 3-3. اگر محصول خریداری نشده بود
                else -> {
                    startPurchaseFlow()
                }
            }
        }

        // 4. شروع فرآیند بررسی موجودی
        mHelper.queryInventoryAsync(inventoryListener)
    }

    private fun developerPayload(purchase: Purchase): Boolean {
        return try {
            val mac = DeviceInfo.getAndroidId(this)
            val sig = purchase.signature
            val date = purchase.originalJson
            val time = formatPurchaseTime(purchase.purchaseTime)
            val sku = purchase.sku
            val token = purchase.token
            //***verfiy purchase
            val verify = "1"
            Toast.makeText(this, "$mac:$sig:$date:$time:$sku:$token", Toast.LENGTH_LONG).show()
            //insertAndVerifyPay(mac, time.toString(), sku, token, sig, verify)
            Security.verifyPurchase(BuildConfig.IAB_PUBLIC_KEY, date, sig)
        } catch (e: Exception) {
            false
        }
    }

    fun setFirstLaunchStatus(isFirstLaunch: Boolean) {
        sharedPreferences.edit() {
            putBoolean(KEY_PREMIUM_PURCHASED, isFirstLaunch)
        } // یا commit() برای اعمال تغییرات به صورت همزمان
    }

    // 5. تابع شروع فرآیند خرید
    private fun startPurchaseFlow() {
        mHelper.launchPurchaseFlow(
            this, SKU_PREMIUM, // شناسه محصول
            RC_REQUEST, // کد درخواست
            IabHelper.OnIabPurchaseFinishedListener { result, info ->
                when {
                    // 5-1. اگر mHelper نال بود
                    false -> return@OnIabPurchaseFinishedListener

                    // 5-2. اگر خطا در فرآیند خرید
                    result?.isFailure == true -> Log.e("TAG", "Error purchasing: $result")

                    // 5-3. اگر اطلاعات خرید نامعتبر
                    info == null || !developerPayload(info) -> {
                        Log.e("TAG", "Purchase authenticity failed")
                    }

                    // 5-4. اگر خرید محصول پریمیوم موفق بود
                    info.sku == SKU_PREMIUM -> handleSuccessfulPurchase(info)
                }
            }, "" // developerPayload
        )
    }

    private fun formatPurchaseTime(purchaseTime: Long): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US)
        sdf.timeZone = TimeZone.getTimeZone("Asia/Tehran") // تنظیم منطقه زمانی ایران
        return sdf.format(Date(purchaseTime))
    }

    // 6. تابع مدیریت خرید موفق
    private fun handleSuccessfulPurchase(purchase: Purchase) {
        Log.d("TAG", "Premium upgrade purchased")
        Toast.makeText(this, "با تشکر از خرید شما!", Toast.LENGTH_LONG).show()


        // 6-1. ذخیره وضعیت خرید
        setFirstLaunchStatus(true)


        goToMain()
        // اگر کاربر قبلاً خریده، ممکن است بخواهید مستقیماً محتوای پریمیوم را نمایش دهید

        // 6-3. مصرف محصول برای امکان خرید مجدد
        mHelper.consumeAsync(purchase) { _, result ->
            if (result?.isFailure == true) {
                Log.e("TAG", "Consumption failed: $result")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mHelper.isInitialized) {
            mHelper.dispose()
        }
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.d("TAG", "onActivityResult($requestCode,$resultCode,$data")

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data)
        } else {
            Log.d("TAG", "....")

        }
    }

}