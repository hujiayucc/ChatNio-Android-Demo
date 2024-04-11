pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://maven.aliyun.com/nexus/content/repositories/releases/")
        google()
        mavenCentral()
    }
}

rootProject.name = "ChatNio"
include(":app")
include(":chatnio-android")
