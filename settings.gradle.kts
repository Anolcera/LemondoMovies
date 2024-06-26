pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "LemondoMovies"
include(":app")
include(":core:network")
include(":core:data")
include(":core:domain")
include(":core:common")
include(":feature:movie-collection")
include(":feature:movie-details")
include(":feature:favorites")
include(":core:ui")
