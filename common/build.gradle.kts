plugins {
    id("multiloader-common")
    id("fabric-loom")
    id("dev.kikugie.fletching-table") version "0.1.0-alpha.22"
}

loom {
    accessWidenerPath = common.project
        .file("../../src/main/resources/accesswideners/${commonMod.minecraft_version}-${commonMod.mod_id}.accesswidener")
}

dependencies {
    minecraft ("com.mojang:minecraft:${commonMod.minecraft_version}")
    mappings (loom.layered {
        officialMojangMappings()
        parchment("org.parchmentmc.data:parchment-${commonMod.parchment_minecraft}:${commonMod.parchment_version}@zip")
    })

    compileOnly ("org.spongepowered:mixin:0.8.7")
    compileOnly ("io.github.llamalad7:mixinextras-common:0.5.0")
    annotationProcessor ("io.github.llamalad7:mixinextras-common:0.5.0")
}
dependencies {
}

val commonJava: Configuration by configurations.creating {
    isCanBeResolved = false
    isCanBeConsumed = true
}

val commonResources: Configuration by configurations.creating {
    isCanBeResolved = false
    isCanBeConsumed = true
}

artifacts {
    afterEvaluate {
        val mainSourceSet = sourceSets.main.get()
        mainSourceSet.java.sourceDirectories.files.forEach {
            add(commonJava.name, it)
        }
        mainSourceSet.resources.sourceDirectories.files.forEach {
            add(commonResources.name, it)
        }
    }
}

