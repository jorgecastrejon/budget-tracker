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
        google()
        mavenCentral()
    }
}
rootProject.name = "BudgetTracker"
include(":app")
include(":core:user")
include(":features:login")
include(":features:register")
include(":foundation:design-system")
include(":libraries:database")
