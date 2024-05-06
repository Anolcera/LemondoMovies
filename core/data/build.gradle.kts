plugins {
    id("lemondomovies.data")
}

android {
    namespace = "anolcera.lemondomovies.core.data"
}

dependencies {
    implementation(project(":core:domain"))
    implementation(project(":core:network"))
}