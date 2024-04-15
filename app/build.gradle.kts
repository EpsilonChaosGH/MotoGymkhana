plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("dagger.hilt.android.plugin")

    id("kotlin-parcelize")

    kotlin("kapt")

    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.motogymkhana"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.motogymkhana"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
    }
    kapt {
        correctErrorTypes = true
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

//    implementation("com.android.support:design:28.0.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    implementation("com.google.dagger:hilt-android:2.44")
    implementation("com.google.firebase:firebase-database:20.3.0")
    kapt("com.google.dagger:hilt-compiler:2.44")
    kaptAndroidTest("com.google.dagger:hilt-compiler:2.44")


    implementation("androidx.room:room-runtime:2.5.1")
    implementation("androidx.room:room-ktx:2.5.1")
    kapt("androidx.room:room-compiler:2.5.1")

    //Navigation components
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.5")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.5")

    //ViewBindDelegate
    implementation("com.github.kirich1409:viewbindingpropertydelegate:1.4.4")
    implementation("com.github.kirich1409:viewbindingpropertydelegate-noreflection:1.4.6")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}