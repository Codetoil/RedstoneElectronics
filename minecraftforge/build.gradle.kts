plugins {
    id("multiloader-loader")
    id("eclipse")
    id("idea")
    id("net.minecraftforge.gradle") version "[6.0.46,6.2)"
    id("org.spongepowered.mixin") version "0.7.+"
    id("org.parchmentmc.librarian.forgegradle") version "1.+"
    id("dev.kikugie.fletching-table.neoforge") version "0.1.0-alpha.22"
}

fletchingTable {
    accessConverter.register("main") {
        add("accesswideners/${commonMod.minecraft_version}-${commonMod.mod_id}.accesswidener")
    }
}

println ("Java: ${System.getProperty("java.version")}," +
        " JVM: ${System.getProperty("java.vm.version")}" +
        " (${System.getProperty("java.vendor")}), Arch: ${System.getProperty("os.arch")}")
minecraft {
    // The mappings can be changed at any time and must be in the following format.
    // Channel:   Version:
    // official   MCVersion             Official field/method names from Mojang mapping files
    // parchment  YYYY.MM.DD-MCVersion  Open community-sourced parameter names and javadocs layered on top of official
    //
    // Parchment is an unofficial project maintained by ParchmentMC, separate from MinecraftForge
    // Additional setup is needed to use their mappings: https://parchmentmc.org/docs/getting-started
    //
    // Simply re-run your setup task after changing the mappings to update your workspace.
    mappings ("parchment", "${commonMod.parchment_version}-${commonMod.parchment_minecraft}")

    // Forge 1.20.6 and newer use official mappings at runtime, so we shouldn't reobf from official to SRG
    reobf = stonecutter.eval(stonecutter.current.version, "<1.20.6")

    // When true, this property will have all Eclipse/IntelliJ IDEA run configurations run the "prepareX" task for the given run configuration before launching the game.
    // In most cases, it is not necessary to enable.
    // enableEclipsePrepareRuns = true
    // enableIdeaPrepareRuns = true

    // This property allows configuring Gradle's ProcessResources task(s) to run on IDE output locations before launching the game.
    // It is REQUIRED to be set to true for this template to function.
    // See https://docs.gradle.org/current/dsl/org.gradle.language.jvm.tasks.ProcessResources.html
    copyIdeResources = true

    // When true, this property will add the folder name of all declared run configurations to generated IDE run configurations.
    // The folder name can be set on a run configuration using the "folderName" property.
    // By default, the folder name of a run configuration is the name of the Gradle project containing it.
    // generateRunFolders = true

    // This property enables access transformers for use in development, applied to the Minecraft artifact.
    // The access transformer file can be anywhere in the project.
    // However, it must be at "META-INF/accesstransformer.cfg" in the final mod jar to be loaded by Forge.
    // This default location is a best practice to automatically put the file in the right place in the final jar.
    // See https://docs.minecraftforge.net/en/latest/advanced/accesstransformers/ for more information.
    // accessTransformer = file('src/main/resources/META-INF/accesstransformer.cfg')

    // Default run configurations.
    // These can be tweaked, removed, or duplicated as needed.
    runs {
        // applies to all the run configs below
        configureEach {
            workingDirectory (project.file("run"))

            // Optional additional logging. The markers can be added/remove as needed, separated by commas.
            // "SCAN": For mods scan.
            // "REGISTRIES": For firing of registry events.
            // "REGISTRYDUMP": For getting the contents of all registries.
//            property 'forge.logging.markers', 'REGISTRIES'

            property ("forge.logging.console.level", "debug")

            // Recommended for development - enables more descriptive errors at the cost of slower startup and registration.
            property ("eventbus.api.strictRuntimeChecks", "true")

            arg ("-mixin.config=${commonMod.mod_id}.mixins.json")

            //it = "MinecraftForge ${it.name.capitalize()} (${project.path})"
        }

        register("client") {
            // Comma-separated list of namespaces to load gametests from. Empty = all namespaces.
            property ("forge.enabledGameTestNamespaces", commonMod.mod_id)
        }

        register("server") {
            property ("forge.enabledGameTestNamespaces", commonMod.mod_id)
            args ("--nogui")
        }

        // This run config launches GameTestServer and runs all registered gametests, then exits.
        // By default, the server will crash when no gametests are provided.
        // The gametest system is also enabled by default for other run configs under the /test command.
        register("gameTestServer") {
            property ("forge.enabledGameTestNamespaces", commonMod.mod_id)
        }

        register("data") {
            // example of overriding the workingDirectory set in configureEach above
            workingDirectory (project.file("run-data"))

            // Specify the modid for data generation, where to output the resulting resource, and where to look for existing resources.
            args ("--mod", commonMod.mod_id, "--all", "--output", file("src/generated/resources/"), "--existing", file("src/main/resources/"))
        }
    }
}

// Include resources generated by data generators.
sourceSets.main {
    resources.srcDir("src/generated/resources")
}

dependencies {
    // Specify the version of Minecraft to use.
    // Any artifact can be supplied so long as it has a "userdev" classifier artifact and is a compatible patcher artifact.
    // The "userdev" classifier will be requested and setup by ForgeGradle.
    // If the group id is "net.minecraft" and the artifact id is one of ["client", "server", "joined"],
    // then special handling is done to allow a setup of a vanilla dependency without the use of an external repository.
    minecraft ("net.minecraftforge:forge:${commonMod.minecraft_version}-${commonMod.forge_version}")

    // Forge 1.21.6+ uses EventBus 7, which shifts most of its runtime validation to compile-time via an annotation processor
    // to improve performance in production environments. This line is required to enable said compile-time validation
    // in your development environment, helping you catch issues early.
    annotationProcessor ("net.minecraftforge:eventbus-validator:7.0-beta.12")

    implementation ("org.spongepowered:mixin:0.8.7")
    annotationProcessor ("org.spongepowered:mixin:0.8.7:processor")

    compileOnly(annotationProcessor("io.github.llamalad7:mixinextras-common:0.5.0") as Any)
    implementation(jarJar("io.github.llamalad7:mixinextras-forge:0.5.0")) {
        jarJar.ranged(this, "[0.5.0,)")
    }
}

mixin {
    // MixinGradle Settings
    add ("main", "${commonMod.mod_id}.refmap.json")
    config ("${commonMod.mod_id}.mixins.json")

    dumpTargetOnFailure = true
}

eclipse {
    // Run everytime eclipse builds the code
    //autoBuildTasks genEclipseRuns
    // Run when importing the project
    synchronizationTasks ("genEclipseRuns")
}

tasks {
    processResources {
        exclude("${commonMod.mod_id}-${commonMod.minecraft_version}.accesswidener")
    }
}