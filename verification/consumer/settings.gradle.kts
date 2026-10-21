pluginManagement { repositories { google(); mavenCentral(); gradlePluginPortal() } }
dependencyResolutionManagement {
    repositories {
        exclusiveContent {
            forRepository { maven { url = uri(providers.gradleProperty("producerRepository").get()) } }
            filter {
                listOf("", "-android", "-jvm", "-macosarm64").forEach { suffix ->
                    includeModule("io.github.kotlinmania", "bitflags-kotlin" + suffix)
                }
            }
        }
        google()
        mavenCentral()
    }
}
rootProject.name = "bitflags-public-api-consumer"
