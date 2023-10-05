import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    kotlin("jvm") version "1.9.10"
    id("org.graalvm.buildtools.native") version "0.9.27"
    id("io.jooby.run") version "3.0.5"
    id("com.github.johnrengelman.shadow") version "7.1.2"

    application

}

group = "com.example"
version = "1.0.0-SNAPSHOT"

val appName = "com.example.starter.AppKt"

repositories {
    mavenCentral()
}

dependencies {
    implementation("io.jooby:jooby-netty:3.0.5")
    implementation("io.jooby:jooby-kotlin:3.0.5")
    implementation(kotlin("stdlib-jdk8"))
    implementation("ch.qos.logback:logback-classic:1.4.7")
}

graalvmNative {
    toolchainDetection.set(true)


    binaries {
        named("main") {

            javaLauncher.set(javaToolchains.launcherFor {
                languageVersion.set(JavaLanguageVersion.of(17))
            })
            imageName.set("application")
            mainClass.set(appName)

//            debug.set(false)
//            verbose.set(true)
//            fallback.set(true)
//            sharedLibrary.set(false)
//            quickBuild.set(false)
//            richOutput.set(true)

//            buildArgs.add("--link-at-build-time")

            useFatJar.set(true)
        }
    }
}



application {
    mainClass.set(appName)
}

tasks.withType<ShadowJar> {
    mergeServiceFiles()
}

tasks {
    joobyRun {
        mainClass = appName
        restartExtensions = mutableListOf("conf", "properties", "class")
        compileExtensions = mutableListOf("java", "kt")
        port = 8000
        waitTimeBeforeRestart = 500
        isUseSingleClassLoader = false
    }
}