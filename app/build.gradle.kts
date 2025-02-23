plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.wiggle"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.wiggle"
        minSdk = 30
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17

            targetCompatibility = JavaVersion.VERSION_17
        }
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
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.06.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //hilt
    implementation("com.google.dagger:hilt-android:2.48")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    kapt("androidx.hilt:hilt-compiler:1.1.0")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    //coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")
    //room
    val room_version = "2.6.1"
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    kapt("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
    //OpenCSV
    implementation("com.opencsv:opencsv:5.5.2")
    //navigation
    implementation("io.github.raamcosta.compose-destinations:core:1.1.2-beta")
    ksp("io.github.raamcosta.compose-destinations:ksp:1.1.2-beta")
    //lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.7.0")

    //paging
    implementation("androidx.paging:paging-compose:3.2.1")
    //coil
    implementation("io.coil-kt:coil-compose:1.3.2")
    implementation("io.coil-kt:coil-svg:1.3.2")
    //okhttp
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.1")
    //accompanist
    implementation("androidx.compose.runtime:runtime-livedata:1.6.4")
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.4")
    //splash
    implementation("androidx.core:core-splashscreen:1.0.0")
    //google fonts
    implementation("androidx.compose.ui:ui-text-google-fonts:1.6.4")
    //icons
    implementation("androidx.compose.material:material-icons-extended:1.6.4")
    //timber
    implementation("com.jakewharton.timber:timber:4.7.1")
    //ktor
    implementation("io.ktor:ktor-client-android:2.3.9")
    //crop
    implementation("com.github.yalantis:ucrop:2.2.8")


//    // Local Unit Tests
//    implementation("androidx.test:core:1.4.0")
//    testImplementation("junit:junit:4.13.2")
//    testImplementation("org.hamcrest:hamcrest-all:1.3")
//    testImplementation("androidx.arch.core:core-testing:2.1.0")
//    testImplementation("org.robolectric:robolectric:4.5.1")
//    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")
//    testImplementation("com.google.truth:truth:1.1.3")
//    testImplementation("com.squareup.okhttp3:mockwebserver:4.9.1")
//    testImplementation("io.mockk:mockk:1.10.5")
//    testImplementation("org.robolectric:robolectric:4.5.1")
//
//    androidTestImplementation("com.google.dagger:hilt-android-testing:2.37")
//
//    // Instrumented Unit Tests
//    androidTestImplementation("junit:junit:4.13.2")
//    androidTestImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.5.0")
//    androidTestImplementation("androidx.arch.core:core-testing:2.1.0")
//    androidTestImplementation("com.google.truth:truth:1.1.3")
//    androidTestImplementation("androidx.test.ext:junit:1.1.3")
//    androidTestImplementation("androidx.test:core-ktx:1.4.0")
//    androidTestImplementation("com.squareup.okhttp3:mockwebserver:4.9.1")
//    androidTestImplementation("io.mockk:mockk-android:1.10.5")

    // Test rules and transitive dependencies:
  //  androidTestImplementation("androidx.compose.ui:ui-test-junit4:$compose_version")
// Needed for createAndroidComposeRule, but not createComposeRule:
    //debugImplementation("androidx.compose.ui:ui-test-manifest:$compose_version")





}
kapt{
    correctErrorTypes = true
}
