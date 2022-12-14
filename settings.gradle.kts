pluginManagement {
    includeBuild("build-logic")
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
include(":benchmark")
include(":core:user")
include(":features:login")
include(":features:register")
include(":features:overview")
include(":foundation:design-system")
include(":libraries:database")
