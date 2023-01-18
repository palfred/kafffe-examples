plugins {
    kotlin("js") version "1.8.0"
}


// Disable Kotlin JS requirement of "gradlew kotlinUpgradeYarnLock" when dependencies has changed:
rootProject.plugins.withType(org.jetbrains.kotlin.gradle.targets.js.yarn.YarnPlugin::class.java) {
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().yarnLockMismatchReport = org.jetbrains.kotlin.gradle.targets.js.yarn.YarnLockMismatchReport.NONE
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().reportNewYarnLock = false // true
    rootProject.the<org.jetbrains.kotlin.gradle.targets.js.yarn.YarnRootExtension>().yarnLockAutoReplace = true // true
}

group = "dk.rheasoft"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    maven {
        name = "GitHubPackages"
        url = uri("https://maven.pkg.github.com/palfred/Kafffe") // Github Package
        credentials {
            val githubUser: String by project
            val githubToken: String by project
            username = githubUser
            password = githubToken

        }
    }
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib-js", "1.8.0"))
    implementation("dk.rheasoft:kafffe:1.2-SNAPSHOT")
    testImplementation(kotlin("test"))
}

kotlin {
    js(IR) {
        binaries.executable()
        browser {
            commonWebpackConfig {
                cssSupport {
                    enabled.set(true)
                }
            }
        }
    }
}