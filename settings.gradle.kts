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
    id("dev.kikugie.stonecutter") version "0.7.10"
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.10.0"
}

// This should match the folder name of the project, or else IDEA may complain (see https://youtrack.jetbrains.com/issue/IDEA-317606)
rootProject.name = "Redstone Electronics"

val commonVersions = providers.gradleProperty("stonecutter_enabled_common_versions")
    .orNull?.split(",")?.map { it.trim() } ?: emptyList()
val fabricVersions = providers.gradleProperty("stonecutter_enabled_fabric_versions")
    .orNull?.split(",")?.map { it.trim() } ?: emptyList()
val minecraftforgeVersions = providers.gradleProperty("stonecutter_enabled_minecraftforge_versions")
    .orNull?.split(",")?.map { it.trim() } ?: emptyList()
val neoforgeVersions = providers.gradleProperty("stonecutter_enabled_neoforge_versions")
    .orNull?.split(",")?.map { it.trim() } ?: emptyList()
val quiltVersions = providers.gradleProperty("stonecutter_enabled_quilt_versions")
    .orNull?.split(",")?.map { it.trim() } ?: emptyList()
val dists = mapOf(
    "common" to commonVersions,
    "fabric" to fabricVersions,
    "minecraftforge" to minecraftforgeVersions,
    "neoforge" to neoforgeVersions,
    "quilt" to quiltVersions
)
val uniqueVersions = dists.values.flatten().distinct()
println(uniqueVersions)

stonecutter {
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