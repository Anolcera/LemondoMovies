import java.util.Properties

plugins {
    id("lemondomovies.android.library")
    id("lemondomovies.android.hilt")
    id("kotlinx-serialization")
}

android {
    namespace = "anolcera.lemondomovies.network"

    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        val properties = Properties()
        properties.load(project.rootProject.file("local.properties").inputStream())

        buildConfigField(
            type = "String",
            name = "THE_MOVIE_DB_API_KEY",
            value = "\"${properties.getProperty("THE_MOVIE_DB_API_KEY")}\""
        )
    }
}

dependencies {

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.kotlin.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.okHttp)
    implementation(libs.okHttp.logging)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}