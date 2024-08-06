import java.util.Properties

include(":specs")


pluginManagement {
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
        // Mapbox Maven repository
        maven {
            url = uri("https://api.mapbox.com/downloads/v2/releases/maven")
            authentication.create<BasicAuthentication>("basic")

            // Do not change the username below. It should always be "mapbox" (not your username).
            credentials.username = "mapbox"
            // Use the secret token stored in keystore.properties as the password
            credentials.password = Properties().getProperty("MAPBOX_DOWNLOADS_SECRET_TOKEN")
            // credentials.password = "sk.eyJ1Ijoib3RhbXVyb2QiLCJhIjoiY2x6Z2VpeWViMWRlcTJrczh3d3h6aGJhbSJ9.TEvt738_u4k6tkoR-4E7rA"
            // credentials.password = providers.gradleProperty("MAPBOX_DOWNLOADS_SECRET_TOKEN").get()
        }
    }
}

rootProject.name = "MyTaxi"
include(":app")
include(":data")
include(":domain")
include(":presentation")
include(":specs")
