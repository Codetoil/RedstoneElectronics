plugins {
    id("multiloader-loader")
    id("org.quiltmc.loom")
    id("dev.kikugie.fletching-table.fabric") version "0.1.0-alpha.22"
}
dependencies {
    minecraft ("com.mojang:minecraft:${commonMod.minecraft_version}")
    mappings (loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${commonMod.parchment_minecraft}:${commonMod.parchment_version}@zip")
    })
    modImplementation ("net.fabricmc:fabric-loader:${commonMod.fabric_loader_version}")
    modImplementation ("net.fabricmc.fabric-api:fabric-api:${commonMod.fabric_version}")

    implementation("io.github.llamalad7:mixinextras-fabric:0.5.0")
}

loom {
    accessWidenerPath = common.project.file("src/main/resources/accesswideners/${commonMod.mod_id}" +
            "-${commonMod.minecraft_version}.accesswidener")

    runs {
        getByName("client") {
            client()
            configName = "Fabric Client"
            ideConfigGenerated(true)
        }
        getByName("server") {
            server()
            configName = "Fabric Server"
            ideConfigGenerated(true)
        }
    }

    mixin {
        defaultRefmapName = "${commonMod.mod_id}.refmap.json"
    }
}

tasks.named<ProcessResources>("processResources") {
    val awFile = project(":common").file("src/main/resources/accesswideners/${commonMod.mod_id}-${commonMod.minecraft_version}.accesswidener")

    from(awFile.parentFile) {
        include(awFile.name)
        rename(awFile.name, "${commonMod.mod_id}.accesswidener")
        into("")
    }
}