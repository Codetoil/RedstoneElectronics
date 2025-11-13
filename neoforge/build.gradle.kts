plugins {
    id("multiloader-loader")
    id("net.neoforged.moddev")
    id("dev.kikugie.fletching-table.neoforge") version "0.1.0-alpha.22"
}

fletchingTable {
    accessConverter.register("main") {
        add("accesswideners/${commonMod.minecraft_version}-${commonMod.mod_id}.accesswidener")
    }
}

neoForge {
    version = commonMod.neoforge_version
    // Automatically enable neoforge AccessTransformers if the file exists
    val at = project(":common").file("src/main/resources/META-INF/accesstransformer.cfg")
    if (at.exists()) {
        accessTransformers.from(at.absolutePath)
    }
    parchment {
        minecraftVersion = commonMod.parchment_minecraft
        mappingsVersion = commonMod.parchment_version
    }
    runs {
        configureEach {
            systemProperty("neoforge.enabledGameTestNamespaces", commonMod.mod_id)
        }
        register("client") {
            client()
        }
        register("serverData") {
            if (stonecutter.eval(stonecutter.current.version, "<=1.21.2")) data() else serverData()

            // DataGen can be run by - "./gradlew :neoforge:runData" in Terminal.
            // Specify the modid for data generation, where to output the resulting resource, and where to look for existing resources.
            programArguments.addAll(
                "--mod",
                commonMod.mod_id,
                "--all",
                "--output",
                file("src/generated/resources/").getAbsolutePath(),
                "--existing",
                file("src/main/resources/").getAbsolutePath()
            )
        }
        register("server") {
            server()
        }
    }
    mods {
        register(commonMod.mod_id) {
            sourceSet(sourceSets.main.get())
        }
    }
}

dependencies {
    implementation(jarJar("io.github.llamalad7:mixinextras-neoforge:0.5.0")) {
        //jarJar.ranged(this, "[0.5.0,)")
    }
}

sourceSets.main {
    resources.srcDir("src/generated/resources")
}

tasks {
    processResources {
        exclude("${commonMod.mod_id}-${commonMod.minecraft_version}.accesswidener")
    }
}

tasks.named("createMinecraftArtifacts") {
    dependsOn(":neoforge:${commonMod.minecraft_version}:processResources")
}