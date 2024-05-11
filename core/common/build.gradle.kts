plugins {
    id("lemondomovies.android.library")
}

android {
    namespace = "anolcera.lemondomovies.core.common"
}

dependencies {
    implementation(libs.kotlinx.coroutines.android)
}