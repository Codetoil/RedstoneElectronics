pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.fabricmc.net/")
        maven("https://maven.quiltmc.org/repository/release/")
        maven("https://maven.neoforged.net/releases/")
        maven("https://maven.minecraftforge.net")
        maven("https://maven.kikugie.dev/snapshots")
        maven("https://maven.kikugie.dev/releases")
    }
}

plugins {
    id("dev.kikugie.stonecutter") version "0.7.6"
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

// This should match the folder name of the project, or else IDEA may complain (see https://youtrack.jetbrains.com/issue/IDEA-317606)
rootProject.name = "Redstone Electronics"

val commonVersions = providers.gradleProperty("stonecutter_enabled_common_versions")
    .orNull?.split(",")?.map { it.trim() } ?: emptyList()
val fabricmcVersions = providers.gradleProperty("stonecutter_enabled_fabricmc_versions")
    .orNull?.split(",")?.map { it.trim() } ?: emptyList()
val minecraftforgeVersions = providers.gradleProperty("stonecutter_enabled_minecraftforge_versions")
    .orNull?.split(",")?.map { it.trim() } ?: emptyList()
val neoforgeVersions = providers.gradleProperty("stonecutter_enabled_neoforge_versions")
    .orNull?.split(",")?.map { it.trim() } ?: emptyList()
val quiltmcVersions = providers.gradleProperty("stonecutter_enabled_quiltmc_versions")
    .orNull?.split(",")?.map { it.trim() } ?: emptyList()
val dists = mapOf(
    "common" to commonVersions,
    "fabricmc" to fabricmcVersions,
    "minecraftforge" to minecraftforgeVersions,
    "neoforge" to neoforgeVersions,
    "quiltmc" to quiltmcVersions
)
val uniqueVersions = dists.values.flatten().distinct()
println(uniqueVersions)

stonecutter {
    kotlinController = true
    centralScript = "build.gradle.kts"

    create(rootProject) {
        versions(*uniqueVersions.toTypedArray())
        vcsVersion = "1.18.2"

        dists.forEach { (branchName, branchVersions) ->
            branch(branchName) {
                versions(*branchVersions.toTypedArray())
            }
        }
    }
}