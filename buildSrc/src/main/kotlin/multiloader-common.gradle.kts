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
            maven("https://maven.neoforged.net/releases") { name = "NeoForge" },
        )
        filter { includeGroup("org.parchmentmc.data") }
    }
    maven("https://maven.quiltmc.org/repository/release/")
    maven("https://maven.minecraftforge.net")
    maven("https://www.cursemaven.com")
    maven("https://api.modrinth.com/maven") {
        name = "Modrinth"
        content {
            includeGroup("maven.modrinth")
        }
    }
    maven("https://maven.resourcefulbees.com/repository/maven-public/") { name = "ResourcefulBees" }
    maven("https://maven.terraformersmc.com/releases/") { name = "TerraformersMC" }
    maven("https://maven.isxander.dev/releases")
    maven("https://maven.isxander.dev/snapshots")
    maven("https://maven.quiltmc.org/repository/release")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://maven.ladysnake.org/releases") { name = "Ladysnake Libs" }
    maven("https://maven.theillusivec4.top/")
    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")

    maven("https://maven.jamieswhiteshirt.com/libs-release") {
        content {
            includeGroup("com.jamieswhitefshirt")
        }
    }

    maven("https://pkgs.dev.azure.com/djtheredstoner/DevAuth/_packaging/public/maven/v1")
}

tasks {
    processResources {
        val expandProps = mapOf(
            "javaVersion" to commonMod.propOrNull("java_version"),
            "modId" to commonMod.mod_id,
            "modName" to commonMod.mod_name,
            "modVersion" to commonMod.version,
            "modGroup" to commonMod.group,
            "modAuthor" to commonMod.mod_author,
            "modDescription" to commonMod.mod_description,
            "modLicense" to commonMod.license,
            "modGitHub" to commonMod.mod_github,
            "modDiscord" to commonMod.mod_author_discord,
            "minecraftVersion" to commonMod.propOrNull("minecraft_version"),
            "fabricLoaderVersion" to commonMod.propOrNull("fabric_loader_version"),
            "fabricApiVersion" to commonMod.propOrNull("fabric_version"),
            "minecraftForgeVersion" to commonMod.propOrNull("minecraftforge_version"),
            "neoForgeVersion" to commonMod.propOrNull("neoforge_version"),
            "quiltLoaderVersion" to commonMod.propOrNull("quilt_loader_version"),
            "quiltedFabricApiVersion" to commonMod.propOrNull("quilted_fabric_api"),
            "minecraftVersion" to commonMod.propOrNull("minecraft_version"),
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
    dependsOn(":common:${commonMod.propOrNull("minecraft_version")}:stonecutterGenerate")
}
