plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("dagger.hilt.android.plugin")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-android")
}

android {
    compileSdk = 31

    defaultConfig {
        applicationId = "co.zmt.themovie"
        minSdk = 23
        targetSdk = 31
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        buildConfigField("String", "BASE_URL", "\"https://api.themoviedb.org/\"")
        buildConfigField("String", "API_KEY", "\"75f07e7064a675deb20de1c946dd28e2\"")
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.6.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
    //Timber for logging
    implementation("com.jakewharton.timber:timber:4.7.1")

    //dagger-hilt
    implementation("com.google.dagger:hilt-android:2.38.1")
    kapt("com.google.dagger:hilt-compiler:2.38.1")

    //Room
    implementation("androidx.room:room-runtime:2.3.0")
    kapt("androidx.room:room-compiler:2.3.0")

    //Coroutine-room
    implementation("androidx.room:room-ktx:2.3.0")

    //Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    //moshi
    implementation("com.squareup.moshi:moshi:1.12.0")
    kapt("com.squareup.moshi:moshi-kotlin-codegen:1.12.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.12.0")
    implementation("com.squareup.moshi:moshi-adapters:1.12.0")

    //Okhttp & logging interceptor
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")

    //androidx security dependency
    implementation("androidx.security:security-crypto:1.1.0-alpha03")

    //architecture components
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.3.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1")
    implementation("androidx.fragment:fragment-ktx:1.3.6")

    //navigation component
    val navVersion = "2.3.5"
    // Kotlin
    implementation("androidx.navigation:navigation-fragment-ktx:$navVersion")
    implementation("androidx.navigation:navigation-ui-ktx:$navVersion")

    //gllide

    implementation("com.github.bumptech.glide:glide:4.12.0")
    kapt("com.github.bumptech.glide:compiler:4.12.0")

}