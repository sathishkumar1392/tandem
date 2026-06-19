plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.sathish.tandem"
    compileSdk  = 37

    defaultConfig {
        applicationId = "com.sathish.tandem"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"https://tandem2019.web.app/\"")
    }

    buildTypes {
        debug {

            // To display log and log interceptor
            buildConfigField("boolean", "LOG", "true")
            buildConfigField( "boolean", "RETROFIT_LOG_INTERCEPTOR", "true")
        }

        release {
            signingConfig = signingConfigs.getByName("debug")
            buildConfigField("boolean", "LOG", "false")
            buildConfigField("boolean", "RETROFIT_LOG_INTERCEPTOR", "false")
            isMinifyEnabled = true
            isShrinkResources = true
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
    buildFeatures {
        compose = true
        buildConfig = true
    }
    configurations.all {
        exclude(group = "com.intellij", module = "annotations")
    }
}

dependencies {
    // AndroidX Libraries
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    // Compose UI
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.compose.icons.extended)
    implementation(libs.androidx.lifecycle.viewmodel.savedstate)

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)



    // Debugging
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)


    // Dependency Injections
    implementation(libs.koin.core)
    implementation(libs.koin.android.compose)
    implementation(libs.koin.android)

    // Coroutines
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.test)
    testImplementation(libs.turbine)

    // Retrofit and OkHttp
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.moshi.kotlin)
    implementation(libs.logging.interceptor)


    // Testing Libraries
    testImplementation(libs.mockk)

    // Coil for Image Loading
    implementation(libs.coil.compose)

    //paging
    implementation(libs.paging.runtime)
    implementation(libs.paging.compose)

    //room
    implementation(libs.room.ktx)
    implementation(libs.room.runtime)
    ksp(libs.room.ksp)
}