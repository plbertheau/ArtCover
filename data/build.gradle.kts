plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.plbertheau.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/data/schemas") // ✅ Required for auto migrations
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    implementation(project(":domain"))
    // ROOM
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.junit.ktx)
    ksp(libs.androidx.room.compiler)

    // HILT
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

    // RETROFIT
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson)

    //OKHTTP
    implementation(libs.okhttp.logging)

    //OKHTTP PROFILER
    implementation(libs.okhttp.profiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    testImplementation("junit:junit:4.13.2")
    testImplementation("androidx.room:room-testing:2.4.2")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.x.x")
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}