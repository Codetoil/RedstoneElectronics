plugins {
    id("java-library")
    id("idea")
}

version = "${loader}-${commonMod.version}+mc${stonecutterBuild.current.version}"

base {
    archivesName = commonMod.mod_id
}

java {
    toolchain.languageVersion = JavaLanguageVersion.of(commonProject.prop("java_version")!!)
    // withSourcesJar()
    // withJavadocJar()
}

repositories {
    mavenCentral()
    exclusiveContent {
        forRepository {
            maven("https://repo.spongepowered.org/repository/maven-public") { name = "Sponge" }
        }
        filter { includeGroupAndSubgroups("org.spongepowered") }
    }
    exclusiveContent {
        forRepositories(
            maven("https://maven.parchmentmc.org") { name = "ParchmentMC" },
        )
        filter { includeGroup("org.parchmentmc.data") }
    }
    maven("https://maven.fabricmc.net/") { name = "FabricMC" }
    maven("https://maven.minecraftforge.net") { name = "MinecraftForge" }
    maven("https://maven.neoforged.net/releases") { name = "NeoForge" }
    maven("https://maven.quiltmc.org/repository/release") { name = "QuiltMC" }
    maven("https://www.cursemaven.com")
    maven("https://api.modrinth.com/maven") {
        name = "Modrinth"
        content {
            includeGroup("maven.modrinth")
        }
    }
}

tasks {
    processResources {
        val expandProps = mapOf(
            "java_version" to commonMod.propOrNull("java_version"),
            "mod_id" to commonMod.mod_id,
            "mod_name" to commonMod.mod_name,
            "version" to commonMod.version,
            "mod_group" to commonMod.group,
            "mod_author" to commonMod.mod_author,
            "mod_description" to commonMod.mod_description,
            "license" to commonMod.license,
            "mod_github" to commonMod.mod_github,
            "mod_author_discord" to commonMod.mod_author_discord,
            "minecraft_version" to commonMod.propOrNull("minecraft_version"),
            "fabric_loader_version" to commonMod.propOrNull("fabric_loader_version"),
            "fabric_api_version" to commonMod.propOrNull("fabric_version"),
            "minecraftforge_version" to commonMod.propOrNull("minecraftforge_version"),
            "neoforge_version" to commonMod.propOrNull("neoforge_version"),
            "quilt_loader_version" to commonMod.propOrNull("quilt_loader_version"),
            "quilted_fabric_api_version" to commonMod.propOrNull("quilted_fabric_api_version"),
            "minecraft_version" to commonMod.propOrNull("minecraft_version"),
            "minecraft_version_range" to commonMod.propOrNull("minecraft_version_range"),
            "neoforge_loader_version_range" to commonMod.propOrNull("neoforge_loader_version_range"),
        ).filterValues { it?.isNotEmpty() == true }.mapValues { (_, v) -> v!! }

        val jsonExpandProps = expandProps.mapValues { (_, v) -> v.replace("\n", "\\\\n") }

        filesMatching(listOf("META-INF/mods.toml", "META-INF/neoforge.mods.toml")) {
            expand(expandProps)
        }

        filesMatching(listOf("pack.mcmeta", "fabric.mod.json", "quilt.mod.json", "*.mixins.json")) {
            expand(jsonExpandProps)
        }

        inputs.properties(expandProps)
    }
}

tasks.named("processResources") {
    dependsOn(":common:${commonMod.minecraft_version}:stonecutterGenerate")
}
