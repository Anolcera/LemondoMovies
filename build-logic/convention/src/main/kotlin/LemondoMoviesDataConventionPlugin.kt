import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.project

class LemondoMoviesDataConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("lemondomovies.android.library")
                apply("lemondomovies.android.hilt")
                apply("kotlinx-serialization")
            }
            val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")
            dependencies {
                add("implementation", libs.findLibrary("androidx.core.ktx").get())
                add("implementation", libs.findLibrary("kotlinx.coroutines.android").get())
                add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
                add("implementation", libs.findLibrary("androidx.core-ktx").get())
                add("implementation", libs.findLibrary("retrofit.core").get())
                add("androidTestImplementation", libs.findLibrary("androidx.junit").get())
                add("testImplementation", libs.findLibrary("junit").get())

                add("implementation", libs.findLibrary("androidx.paging").get())

                add("implementation", libs.findLibrary("androidx.room").get())
                add("kapt", libs.findLibrary("androidx.room.compiler").get())
                add("implementation", libs.findLibrary("androidx.room.paging").get())

                add("implementation", project(":core:domain"))
                add("implementation", project(":core:network"))
                add("implementation", project(":core:common"))
            }
        }
    }
}