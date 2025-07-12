plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.behnamuix.colordetect"
    compileSdk = 35
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.behnamuix.colordetect"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        val marketApplicationId = "ir.mservices.market"
        val marketBindAddress = "ir.mservices.market.InAppBillingService.BIND"
        manifestPlaceholders.apply {
            this["marketApplicationId"] = marketApplicationId
            this["marketBindAddress"] = marketBindAddress
            this["marketPermission"] = "${marketApplicationId}.BILLING"
        }
        buildConfigField(
            "String",
            "IAB_PUBLIC_KEY",
            "\"MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCEtnZuQ8I2hAhW6Vq7dEIG4znymRtk3kr/RSMuYgBuavsurhMIiXspjJhGa2163CN8xLokiP6YOYcgrSIpn4iDOjrFHYj4Hs93b+lz9ZyaxBJZ7gOa66rKFs5LKMquOM7cok14Cql4Ud+gT/QxkMezAH5xpwYmrbeSIzECnXX2qQIDAQAB\""
        )

        buildTypes {
            release {
                isMinifyEnabled = false
                proguardFiles(
                    getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_11
            targetCompatibility = JavaVersion.VERSION_11
        }
        kotlinOptions {
            jvmTarget = "11"
        }
        buildFeatures {
            compose = true
        }
    }

    dependencies {

        implementation(libs.androidx.core.ktx)
        implementation(libs.androidx.lifecycle.runtime.ktx)
        implementation(libs.androidx.activity.compose)
        implementation(platform(libs.androidx.compose.bom))
        implementation(libs.androidx.ui)
        implementation(libs.androidx.ui.graphics)
        implementation(libs.androidx.ui.tooling.preview)
        implementation(libs.androidx.material3)
        testImplementation(libs.junit)
        androidTestImplementation(libs.androidx.junit)
        androidTestImplementation(libs.androidx.espresso.core)
        androidTestImplementation(platform(libs.androidx.compose.bom))
        androidTestImplementation(libs.androidx.ui.test.junit4)
        debugImplementation(libs.androidx.ui.tooling)
        debugImplementation(libs.androidx.ui.test.manifest)


        // CameraX
        implementation("androidx.camera:camera-core:1.3.0")
        implementation("androidx.camera:camera-camera2:1.3.0")
        implementation("androidx.camera:camera-lifecycle:1.3.0")
        implementation("androidx.camera:camera-view:1.3.0")
        implementation ("androidx.lifecycle:lifecycle-runtime-compose:2.6.0")

        // Permissions
        implementation("com.google.accompanist:accompanist-permissions:0.32.0")

        implementation("androidx.core:core-ktx:1.13.1")

        implementation("com.github.myketstore:myket-billing-client:1.6")

        implementation("com.github.ZarinPal:Android-SDK-Kotlin:1.1.1")


    }
}

