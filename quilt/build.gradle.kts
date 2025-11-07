plugins {
    id("multiloader-loader")
    id("org.quiltmc.loom")
    id("dev.kikugie.fletching-table.fabric") version "0.1.0-alpha.22"
}

loom {
    accessWidenerPath = common.project.file("src/main/resources/accesswideners/${commonMod.mod_id}" +
            "-${commonMod.minecraft_version}.accesswidener")

    runs {
        getByName("client") {
            client()
            configName = "Quilt Client"
            ideConfigGenerated(true)
        }
        getByName("server") {
            server()
            configName = "Quilt Server"
            ideConfigGenerated(true)
        }
    }

    mixin {
        defaultRefmapName = "${commonMod.mod_id}.refmap.json"
    }
}

tasks.named<ProcessResources>("processResources") {
    val awFile = project(":common").file("src/main/resources/accesswideners/${commonMod.mod_id}.accesswidener")

    from(awFile.parentFile) {
        include(awFile.name)
        rename(awFile.name, "${commonMod.mod_id}.accesswidener")
        into("")
    }
}

// All the dependencies are declared at gradle/libs.version.toml and referenced with "libs.<id>"
// See https://docs.gradle.org/current/userguide/platforms.html for information on how version catalogs work.
dependencies {
    minecraft("com.mojang:minecraft:${commonMod.minecraft_version}")
    mappings(loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${commonMod.parchment_minecraft}:${commonMod.parchment_version}@zip")
    })
    modImplementation("org.quiltmc:quilt-loader:${commonMod.quilt_loader_version}")

    // QSL is not a complete API; You will need Quilted Fabric API to fill in the gaps.
    // Quilted Fabric API will automatically pull in the correct QSL version.
    modImplementation("org.quiltmc.quilted-fabric-api:quilted-fabric-api:${commonMod.quilted_fabric_api_version}")

    implementation("io.github.llamalad7:mixinextras-fabric:0.5.0")
}