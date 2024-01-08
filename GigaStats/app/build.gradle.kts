

plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.giga_stats"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.giga_stats"
        minSdk = 24
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }


}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("androidx.room:room-runtime:2.4.0")
    implementation("androidx.room:room-common:2.5.2")
    implementation(fileTree(mapOf(
        "dir" to "C:\\Users\\giber\\AppData\\Local\\Android\\Sdk\\platforms\\android-33",
        "include" to listOf("*.aar", "*.jar"),
        "exclude" to listOf()
    )))
    annotationProcessor ("androidx.room:room-compiler:2.4.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("com.google.android.material:material:1.4.0") // Für die TabLayout-Integration

}




