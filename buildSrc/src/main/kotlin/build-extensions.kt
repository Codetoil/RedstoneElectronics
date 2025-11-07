import dev.kikugie.stonecutter.build.StonecutterBuildExtension
import dev.kikugie.stonecutter.controller.StonecutterControllerExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.getByType

val Project.mod: ModData get() = ModData(this)
fun Project.prop(key: String): String? = findProperty(key)?.toString()


val Project.stonecutterBuild get() = extensions.getByType<StonecutterBuildExtension>()
val Project.stonecutterController get() = extensions.getByType<StonecutterControllerExtension>()

val Project.common
    get() = requireNotNull(stonecutterBuild.node.sibling("common")) {
        "No common project for $project"
    }
val Project.commonProject get() = rootProject.project(stonecutterBuild.current.project)
val Project.commonMod get() = commonProject.mod

val Project.loader: String? get() = prop("loader")

@JvmInline
value class ModData(private val project: Project) {
    val mod_id: String get() = prop("mod_id")
    val mod_name: String get() = prop("mod_name")
    val version: String get() = prop("version")
    val group: String get() = prop("group")
    val mod_author: String get() = prop("mod_author")
    val mod_description: String get() = prop("mod_description")
    val license: String get() = prop("license")
    val mod_github: String get() = prop("mod_github")
    val mod_author_discord: String get() = prop("mod_author_discord")
    val minecraft_version: String get() = prop("minecraft_version")
    val parchment_minecraft: String get() = prop("parchment_minecraft")
    val parchment_version: String get() = prop("parchment_version")
    val fabric_loader_version: String get() = prop("fabric_loader_version")
    val fabric_version: String get() = prop("fabric_version")
    val forge_version: String get() = prop("forge_version")
    val neoforge_version: String get() = prop("neoforge_version")
    val quilt_loader_version: String get() = prop("quilt_loader_version")
    val quilted_fabric_api_version: String get() = prop("quilted_fabric_api_version")

    fun propOrNull(key: String) = project.prop(key)
    fun prop(key: String) = requireNotNull(propOrNull(key)) { "Missing '$key'" }
}