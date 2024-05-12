plugins {
    id("lemondomovies.android.library")
    id("lemondomovies.android.library.compose")
}

android {
    namespace = "anolcera.lemondomovies.ui"
}

dependencies {
    implementation(libs.androidx.material3)
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.kt.compose.svg)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}