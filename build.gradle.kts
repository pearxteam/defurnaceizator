import net.minecraftforge.gradle.common.BaseExtension
import net.minecraftforge.gradle.user.UserBaseExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.jetbrains.kotlin.jvm")
    id("net.minecraftforge.gradle.forge")
}

val modId: String by project
val modVersion: String by project
val modGroup: String by project
val modDescription: String by project
val modDependencies: String by project
val jdkVersion: String by project
val forgeVersion: String by project
val mcpMappings: String by project
val mcVersion: String by project
val acceptedMcVersions: String by project
val forgelinVersion: String by project
val jeiVersion: String by project


version = modVersion
group = modGroup
description = modDescription

configure<BasePluginConvention> {
    archivesBaseName = "$modId-$mcVersion"
}

configure<JavaPluginConvention> {
    sourceCompatibility = JavaVersion.VERSION_1_8
}

configure<UserBaseExtension> {
    version = "$mcVersion-$forgeVersion"
    runDir = "run"
    mappings = mcpMappings
    replace("VERSION = \"\"", "VERSION = \"$modVersion\"")
    replace("DESCRIPTION = \"\"", "DESCRIPTION = \"$modDescription\"")
    replace("ACCEPTED_MINECRAFT_VERSIONS = \"\"", "ACCEPTED_MINECRAFT_VERSIONS = \"$acceptedMcVersions\"")
    replace("DEPENDENCIES = \"\"", "DEPENDENCIES = \"$modDependencies\"")
    replaceIn("Reference.kt")
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = "1.$jdkVersion"
        kotlinOptions.freeCompilerArgs = listOf("-Xno-param-assertions")
    }
    withType<Jar> {
        manifest {
            attributes(mapOf("FMLAT" to "pmdumper_at.cfg"))
        }
    }
}

repositories {
    maven { url = uri("https://maven.shadowfacts.net/") }
    maven { url = uri("https://minecraft.curseforge.com/api/maven") }
    maven { url = uri("http://dvs1.progwml6.com/files/maven") }
}

dependencies {
    "compile"("net.shadowfacts:Forgelin:$forgelinVersion")
    "runtime"("mezz.jei:jei_$mcVersion:$jeiVersion")
}
