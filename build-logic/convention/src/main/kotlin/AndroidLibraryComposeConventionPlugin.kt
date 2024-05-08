@file:Suppress("unused")

import anolcera.buildlogic.configureAndroidCompose
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class AndroidLibraryComposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.android.library")
            pluginManager.apply("org.jetbrains.kotlin.plugin.parcelize")

            extensions.configure<LibraryExtension> {
                configureAndroidCompose(this)
            }
        }
    }
}