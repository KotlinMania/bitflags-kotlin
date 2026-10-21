import java.security.MessageDigest
import java.util.Properties
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsEnvSpec
import org.jetbrains.kotlin.gradle.targets.js.nodejs.NodeJsRootExtension
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootEnvSpec
import org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension
import org.jetbrains.kotlin.gradle.targets.wasm.nodejs.WasmNodeJsEnvSpec
import org.jetbrains.kotlin.gradle.targets.wasm.yarn.WasmYarnRootEnvSpec
import groovy.json.JsonOutput
import org.gradle.api.artifacts.component.ModuleComponentIdentifier
import org.gradle.api.artifacts.component.ModuleComponentSelector
import org.gradle.api.artifacts.result.ResolvedDependencyResult
import org.gradle.api.tasks.testing.Test

plugins {
    kotlin("multiplatform") version "2.4.20"
    id("com.android.kotlin.multiplatform.library") version "9.4.1"
}

kotlin {
    jvmToolchain(21)
    compilerOptions {
        allWarningsAsErrors.set(true)
    }
    jvm()
    macosArm64()
    js { nodejs() }
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs { nodejs() }
    @OptIn(ExperimentalWasmDsl::class)
    wasmWasi { nodejs() }
    android {
        namespace = "io.github.kotlinmania.bitflags.consumer"
        compileSdk = 34
        minSdk = 24
        withHostTestBuilder {}.configure {}
    }
    sourceSets {
        commonMain.dependencies {
            implementation("io.github.kotlinmania:bitflags-kotlin:${providers.gradleProperty("producerVersion").get()}")
        }
        commonTest.dependencies {
            implementation(kotlin("test"))
        }
    }
}

// Force actual fixture test execution even when validating the same staged version again.
val hostTests = tasks.withType<Test>().matching { it.name == "testAndroidHostTest" }
tasks.withType<Test>().configureEach { outputs.upToDateWhen { false } }

kotlin.targets.named("android") {
    compilations.named("main") {
        val compileDependencies = configurations.getByName(compileDependencyConfigurationName)
        val runtimeDependencies = configurations.named(
            compileDependencyConfigurationName.replace("CompileClasspath", "RuntimeClasspath"),
        )
        val classpathReceipt = layout.buildDirectory.file("reports/publication/raw-classpaths.json")
        val writeClasspath = tasks.register("writeAndroidPublicationClasspath") {
            outputs.file(classpathReceipt)
            outputs.upToDateWhen { false }
            doLast {
                val paths = mapOf(
                    "compile" to compileDependencies,
                    "runtime" to runtimeDependencies.get(),
                ).mapValues { (scope, configuration) ->
                    val artifacts = configuration.incoming.artifacts.artifacts.map { it.file }.distinct()
                    check(artifacts.isNotEmpty() && artifacts.all { it.extension in setOf("aar", "jar") }) {
                        "Unsupported raw Android $scope classpath: $artifacts"
                    }
                    artifacts.sortedBy { it.absolutePath }.map { it.absolutePath }
                }
                classpathReceipt.get().asFile.apply {
                    parentFile.mkdirs()
                    writeText(JsonOutput.prettyPrint(JsonOutput.toJson(paths)) + "\n")
                }
                val selected = compileDependencies.incoming.resolutionResult.rootComponent.get().dependencies
                    .filterIsInstance<ResolvedDependencyResult>().filter { dependency ->
                        (dependency.requested as? ModuleComponentSelector)?.module == "bitflags-kotlin"
                    }
                check(selected.size == 1) { "Expected exactly one BitFlags root dependency" }
                val variant = selected.single().resolvedVariant.externalVariant
                    .orElse(selected.single().resolvedVariant)
                val owner = variant.owner as ModuleComponentIdentifier
                val attributes = variant.attributes.keySet().associate {
                    it.name to variant.attributes.getAttribute(it).toString()
                }
                check(owner.group == "io.github.kotlinmania" && owner.module == "bitflags-kotlin-android" &&
                    owner.version == providers.gradleProperty("producerVersion").get())
                check(attributes["org.jetbrains.kotlin.platform.type"] == "androidJvm" &&
                    attributes["org.gradle.libraryelements"] == "aar" &&
                    attributes["org.gradle.jvm.environment"] == "android")
                layout.buildDirectory.file("reports/publication/selected-variant.json").get().asFile.writeText(
                    JsonOutput.prettyPrint(JsonOutput.toJson(mapOf(
                        "group" to owner.group, "module" to owner.module, "version" to owner.version,
                        "attributes" to attributes,
                    ))) + "\n",
                )
            }
        }
        val checkClasspath = tasks.register<Exec>("checkAndroidPublicationClasspath") {
            dependsOn(writeClasspath)
            mustRunAfter(hostTests)
            commandLine("python3", file("../check_android_classpath.py"), classpathReceipt.get().asFile,
                "--configurations", "--receipt", layout.buildDirectory.file("reports/publication/duplicates.json").get().asFile)
        }
        // Preserve diagnostics after a test failure without converting either result to success.
        hostTests.configureEach { finalizedBy(checkClasspath) }
    }
}

// Retain selected JVM bytes independently of the Android classpath receipt.
kotlin.targets.named("jvm") {
    compilations.named("main") {
        val compileDependencies = configurations.getByName(compileDependencyConfigurationName)
        val runtimeDependencies = configurations.named(
            compileDependencyConfigurationName.replace("CompileClasspath", "RuntimeClasspath"),
        )
        val receipt = layout.buildDirectory.file("reports/publication/jvm-classpaths.json")
        val writeJvmClasspath = tasks.register("writeJvmPublicationClasspath") {
            outputs.file(receipt)
            outputs.upToDateWhen { false }
            doLast {
                val paths = mapOf("compile" to compileDependencies, "runtime" to runtimeDependencies.get())
                    .mapValues { (_, configuration) ->
                        configuration.incoming.artifacts.artifacts.map { it.file }.distinct()
                            .also { artifacts -> check(artifacts.isNotEmpty() && artifacts.all { it.extension == "jar" }) }
                            .sortedBy { it.absolutePath }.map { it.absolutePath }
                    }
                receipt.get().asFile.apply {
                    parentFile.mkdirs()
                    writeText(JsonOutput.prettyPrint(JsonOutput.toJson(paths)) + "\n")
                }
            }
        }
        tasks.named("jvmTest") { finalizedBy(writeJvmClasspath) }
    }
}

// Use the producer's existing test-toolchain pins without selecting new versions.
val producerProperties = Properties().apply {
    file("../../gradle.properties").inputStream().use { load(it) }
}
rootProject.extensions.configure<NodeJsEnvSpec>("kotlinNodeJsSpec") {
    version.set(producerProperties.getProperty("node.version"))
}
rootProject.extensions.configure<WasmNodeJsEnvSpec>("kotlinWasmNodeJsSpec") {
    version.set(producerProperties.getProperty("wasm.node.version"))
}
rootProject.extensions.configure<YarnRootEnvSpec>("kotlinYarnSpec") {
    version.set(producerProperties.getProperty("yarn.version"))
    yarnLockAutoReplace.set(true)
}
rootProject.extensions.configure<WasmYarnRootEnvSpec>("kotlinWasmYarnSpec") {
    version.set(producerProperties.getProperty("wasm.yarn.version"))
    yarnLockAutoReplace.set(true)
}
rootProject.extensions.configure<YarnRootExtension>("kotlinYarn") {
    producerProperties.stringPropertyNames().filter { it.startsWith("yarn.resolution.") }.forEach { key ->
        val module = key.removePrefix("yarn.resolution.")
        resolution(module, producerProperties.getProperty(key))
        resolution("**/$module", producerProperties.getProperty(key))
    }
}
rootProject.extensions.configure<NodeJsRootExtension>("kotlinNodeJs") {
    versions.mocha.version = "12.0.0-beta-10"
    versions.kotlinWebHelpers.version = "3.1.0"
}

listOf("js" to "jsNodeTest", "wasmJs" to "wasmJsNodeTest", "wasmWasi" to "wasmWasiNodeTest").forEach { (target, test) ->
    val compilation = kotlin.targets.getByName(target).compilations.getByName("main")
    val dependencies = configurations.getByName(compilation.compileDependencyConfigurationName)
    val receipt = tasks.register("write${target.replaceFirstChar { it.uppercase() }}PublicationClasspath") {
        doLast {
            val entries = dependencies.incoming.artifacts.artifacts.map { artifact ->
                mapOf(
                    "component" to artifact.id.componentIdentifier.displayName,
                    "file" to artifact.file.absolutePath,
                    "sha256" to MessageDigest.getInstance("SHA-256").digest(artifact.file.readBytes())
                        .joinToString("") { "%02x".format(it) },
                    "attributes" to artifact.variant.attributes.keySet().associate {
                        it.name to artifact.variant.attributes.getAttribute(it).toString()
                    },
                )
            }
            layout.buildDirectory.file("reports/publication/$target-classpath.json").get().asFile.apply {
                parentFile.mkdirs()
                writeText(JsonOutput.prettyPrint(JsonOutput.toJson(entries)) + "\n")
            }
        }
    }
    tasks.named(test) {
        outputs.upToDateWhen { false }
        finalizedBy(receipt)
        dependsOn(if (target == "js") "kotlinUpgradeYarnLock" else "kotlinWasmUpgradeYarnLock")
    }
}
